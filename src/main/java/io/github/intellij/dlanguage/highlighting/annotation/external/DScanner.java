package io.github.intellij.dlanguage.highlighting.annotation.external;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessOutputTypes;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import io.github.intellij.dlanguage.DlangSdkType;
import io.github.intellij.dlanguage.highlighting.annotation.DProblem;
import io.github.intellij.dlanguage.settings.ToolKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import io.github.intellij.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DScanner implements DlangLinter {

    private static final Logger LOG = Logger.getInstance(DScanner.class);

    private static int getDocumentLineCount(final Document document) {
        try {
            final int lineCount = document.getLineCount();
            return lineCount == 0 ? 1 : lineCount;
        } catch (final Exception e) {
            return 1;
        }
    }

    @NotNull
    public DProblem[] checkFileSyntax(@NotNull final PsiFile file) {
        final String dscannerPath = ToolKey.DSCANNER_KEY.getPath();
        if (StringUtil.isEmpty(dscannerPath)) return new DProblem[] {};

        final GeneralCommandLine cmd = buildDscannerCommand(file, dscannerPath);
        try {
            return runDscannerCommand(file, cmd).toArray(new DProblem[] {});
        } catch (java.util.concurrent.ExecutionException | TimeoutException | InterruptedException e) {
            LOG.warn("Problem running DScanner", e);
        }
        return new DProblem[] {};
    }

    private static int getOffsetStart(final PsiFile file, final int startLine, final int startColumn) {
        if(file.getProject().isDisposed()) {
            return -1;
        }

        return ApplicationManager.getApplication().runReadAction((Computable<Integer>) () -> {
            final Document document = PsiDocumentManager
                .getInstance(file.getProject())
                .getDocument(file);
            final int line = getValidLineNumber(startLine, document);
            final int offset = StringUtil.lineColToOffset(file.getText(), line, startColumn - 1);
            return Math.max(offset, 1);
        });
    }

    private static int getLineEndOffset(final Document document, final int line) {
        try {
            return document.getLineEndOffset(line);
        } catch (final Exception e) {
            return 1;
        }
    }

    private GeneralCommandLine buildDscannerCommand(final PsiFile file, final String dscannerPath) {
        final String filePath = file.getVirtualFile().getCanonicalPath();
        final String workingDirectory = file.getProject().getBasePath();

        final GeneralCommandLine cmd = new GeneralCommandLine()
            .withWorkDirectory(workingDirectory)
            .withExePath(dscannerPath);

        final ParametersList args = cmd.getParametersList();
        args.addParametersString("-S");
        args.addParametersString(filePath);

        final String flags = ToolKey.DSCANNER_KEY.getFlags();

        if (DUtil.isNotNullOrEmpty(flags)) {
            args.addAll(flags);
        }

        // try to auto add the compiler sources
        final List<String> compilerSources = getCompilerSourcePaths(file);
        for (final String s : compilerSources) {
            args.addParametersString("-I");
            args.addParametersString(s);
        }

        return cmd;
    }

    private List<Problem> runDscannerCommand(final PsiFile file, final GeneralCommandLine cmd) throws java.util.concurrent.ExecutionException, InterruptedException, TimeoutException {
        return ApplicationManager.getApplication().executeOnPooledThread(() -> {
            final List<Problem> problems = new ArrayList<>();

            try {
                LOG.trace("Starting DScanner process");

                final OSProcessHandler process = new OSProcessHandler(cmd.createProcess(), cmd.getCommandLineString());
                process.addProcessListener(new ProcessAdapter() {
                    @Override
                    public void onTextAvailable(@NotNull final ProcessEvent event, @NotNull final Key outputType) {
                        // we don't care about "system" or "stderr"
                        if(ProcessOutputTypes.STDOUT.equals(outputType)) {
                            parseProblem(event.getText(), file)
                                .ifPresent(problems::add);
                        } else if(ProcessOutputTypes.STDERR.equals(outputType)) {
                            LOG.warn(event.getText());
                            final Notification notification = new Notification("DScanner Error", "DScanner Error", event.getText(), NotificationType.ERROR);
                            Notifications.Bus.notify(notification, file.getProject());
                        }
                    }
                });

                process.startNotify();
                process.waitFor(); // fails here if on wrong thread

                final Integer exitCode = process.getExitCode(); // 0 or 1 depending on whether DScanner found problems

                if(Integer.valueOf(1).equals(exitCode)) {
                    LOG.debug(String.format("DScanner found %s lint problems", problems.size()));
                }
            } catch (final ExecutionException e) {
                LOG.error("There was a problem running DScanner", e);
            }
            return problems;
        }).get(3, TimeUnit.SECONDS);
    }

    private List<String> getCompilerSourcePaths(final PsiFile file) {
        @Nullable final Module module = ProjectRootManager.getInstance(file.getProject())
                                                            .getFileIndex()
                                                            .getModuleForFile(file.getVirtualFile());

        final ArrayList<String> compilerSourcePaths = new ArrayList<>();

        if(module != null && !module.isDisposed()) {
            @Nullable final Sdk sdk = ModuleRootManager.getInstance(module).getSdk();

            if (sdk != null && (sdk.getSdkType() instanceof DlangSdkType)) {
                for (VirtualFile f: sdk.getSdkModificator().getRoots(OrderRootType.SOURCES)) {
                    if (f.exists() && f.isDirectory()) {
                        compilerSourcePaths.add(f.getPath());
                    }
                }
            }
        }

        return compilerSourcePaths;
    }

    private static int getValidLineNumber(int line, final Document document) {
        final int lineCount = getDocumentLineCount(document);
        line = line - 1;
        if (line <= 0) {
            line = 1;
        } else if (line >= lineCount) {
            line = lineCount - 1;
        }
        return line;
    }

    private static int getOffsetEndFallback(final PsiFile file, int line) {
        final Document document = PsiDocumentManager.getInstance(file.getProject()).getDocument(file);
        line = getValidLineNumber(line, document);
        return getLineEndOffset(document, line);
    }

    private static int getOffsetEnd(final PsiFile file, final int offsetStart, final int line) {
        try {
            final String fileText = file.getText(); // Read access is allowed from inside read-action (or EDT)
            int width = 0;
            while (offsetStart + width < fileText.length()) {
                final char c = fileText.charAt(offsetStart + width);
                if (StringUtil.isLineBreak(c)) {
                    break;
                }
                ++width;
            }
            return offsetStart + width;
        } catch (final Exception e) {
            return getOffsetEndFallback(file, line);
        }
    }

    private static TextRange calculateTextRange(final PsiFile file, final int line, final int column) {
        final int startOffset = getOffsetStart(file, line, column);
        final int endOffset = getOffsetEnd(file, startOffset, line);
        return new TextRange(startOffset, endOffset);
    }

    // Example output from DScanner:
    // hello.d(1:7)[error]: Expected identifier instead of ;
    // ddbc/source/ddbc/drivers/mysqlddbc.d(1273:35)[warn]: Line is longer than 120 characters
    private Optional<Problem> parseProblem(final String lint, final PsiFile file) {
        LOG.debug(lint);

        final Pattern p = Pattern.compile("\\w+\\.d\\((\\d+):(\\d+)\\)\\[(\\w+)\\]:(.+)");
        final Matcher m = p.matcher(lint);

        if (m.find()) {
            final int line = Integer.valueOf(m.group(1));
            final int column = Integer.valueOf(m.group(2));
            final TextRange range = calculateTextRange(file, line, column);
            final String severity = m.group(3);
            final String message = StringUtil.trim(m.group(4));

            final Problem problem = new Problem(range, message, severity);
            return Optional.of(problem);
        }
        return Optional.empty();
    }

    public static class Problem extends DProblem {
        private final String severity;
        private final TextRange range;
        private final String message;

        Problem(final TextRange range, final String message, final String severity) {
            this.range = range;
            this.severity = severity;
            this.message = message;
        }

        @Override
        public void createAnnotations(@NotNull final PsiFile file, @NotNull final AnnotationHolder holder) {
            if ("error".equals(severity)) {
                holder.newAnnotation(HighlightSeverity.ERROR, message).range(range).create();
            } else if (message.contains("undocumented")) {
                holder.newAnnotation(HighlightSeverity.INFORMATION, message).range(range).create();
            } else {
                holder.newAnnotation(HighlightSeverity.WARNING, message).range(range).create();
            }
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("[");
            sb.append(severity).append("] ")
                .append(range)
                .append(" ")
                .append(message);
            return sb.toString();
        }
    }

}
