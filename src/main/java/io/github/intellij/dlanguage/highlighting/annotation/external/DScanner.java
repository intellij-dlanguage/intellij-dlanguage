package io.github.intellij.dlanguage.highlighting.annotation.external;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessOutputTypes;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.util.ThrowableRunnable;
import io.github.intellij.dlanguage.highlighting.annotation.DAnnotationHolder;
import io.github.intellij.dlanguage.highlighting.annotation.DProblem;
import io.github.intellij.dlanguage.highlighting.annotation.Problems;
import io.github.intellij.dlanguage.settings.ToolKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public class DScanner {

    private static final Logger LOG = Logger.getInstance(DScanner.class);

    private static int getDocumentLineCount(final Document document) {
        try {
            final int lineCount = document.getLineCount();
            return lineCount == 0 ? 1 : lineCount;
        } catch (final Exception e) {
            return 1;
        }
    }

    Problems checkFileSyntax(@NotNull final PsiFile file) {
        final String dscannerPath = ToolKey.DSCANNER_KEY.getPath();
        if (dscannerPath == null) return new Problems();

        final Problems problems = new Problems();
        problems.addAllNotNull(processFile(file, dscannerPath));
        return problems;
    }

    private static int getOffsetStart(final PsiFile file, final int startLine, final int startColumn) {
        final int[] retVal = {-1};
        final ThrowableRunnable getOffset = new ThrowableRunnable() {
            public void run() {
                final Document document = PsiDocumentManager.getInstance(file.getProject())
                    .getDocument(file);
                final int line = getValidLineNumber(startLine, document);
                final int offset = StringUtil
                    .lineColToOffset(file.getText(), line, startColumn - 1);
                retVal[0] = offset <= 1 ? 1 : offset;
            }
        };
        ReadAction.run(getOffset);
        return retVal[0];
    }

    private static int getLineEndOffset(final Document document, final int line) {
        try {
            return document.getLineEndOffset(line);
        } catch (final Exception e) {
            return 1;
        }
    }

    private List<Problem> processFile(final PsiFile file, final String dscannerPath) {
        final String filePath = file.getVirtualFile().getCanonicalPath();
        final String workingDirectory = file.getProject().getBasePath();

        final GeneralCommandLine cmd = new GeneralCommandLine();
        cmd.setWorkDirectory(workingDirectory);
        cmd.setExePath(dscannerPath);

        final ParametersList args = cmd.getParametersList();
        args.addParametersString("-S");
        args.addParametersString(filePath);

        final List<Problem> problems = new ArrayList<>();

        try {
            final OSProcessHandler process = new OSProcessHandler(cmd.createProcess(), cmd.getCommandLineString());
            process.addProcessListener(new ProcessAdapter() {
                @Override
                public void onTextAvailable(final ProcessEvent event, final Key outputType) {
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
            process.waitFor();

            final Integer exitCode = process.getExitCode(); // 0 or 1 depending on whether DScanner found problems

            if(Integer.valueOf(1).equals(exitCode)) {
                LOG.debug("DScanner found lint problems");
            }
        } catch (final ExecutionException e) {
            LOG.error("There was a problem running DScanner", e);
        }
        return problems;
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
            final String fileText = file.getText();
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

    // hello.d(1:7)[error]: Expected identifier instead of ;
    private Optional<Problem> parseProblem(final String lint, final PsiFile file) {
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
        public void createAnnotations(@NotNull final PsiFile file, @NotNull final DAnnotationHolder holder) {
            if ("error".equals(severity)) {
                holder.createErrorAnnotation(range, message);
            } else if (message.contains("undocumented")) {
                holder.createInfoAnnotation(range, message);
            } else {
                holder.createWarningAnnotation(range, message);
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
