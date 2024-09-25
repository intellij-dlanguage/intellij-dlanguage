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

    @NotNull
    public DProblem[] checkFileSyntax(@NotNull final PsiFile file) {
        if (file.getProject().isDisposed()) {
            LOG.debug("Won't check file syntax with DScanner as project is disposed");
            return new DProblem[] {};
        }

        if (!file.getVirtualFile().isValid()) {
            LOG.debug("Won't check file syntax with DScanner as VirtualFile is not valid");
            return new DProblem[] {};
        }

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
                            if (event.getText().contains("[error]")) {
                                final Notification notification = new Notification("DScanner Error", "DScanner Error", event.getText(), NotificationType.ERROR);
                                Notifications.Bus.notify(notification, file.getProject());
                            }
                        }
                    }
                });

                process.startNotify();
                process.waitFor(); // fails here if on wrong thread

                final Integer exitCode = process.getExitCode(); // 0 or 1 depending on whether DScanner found problems

                if(Integer.valueOf(1).equals(exitCode)) {
                    LOG.info(String.format("DScanner found %s lint problems in %s", problems.size(), file.getName()));
                }
            } catch (final ExecutionException e) {
                LOG.error("There was a problem running DScanner", e);
            }
            return problems;
        }).get(3, TimeUnit.SECONDS);
    }

    private List<String> getCompilerSourcePaths(final PsiFile file) {
        return ApplicationManager.getApplication().runReadAction((Computable<List<String>>) () -> {
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
        });
    }

    // Example output from DScanner:
    // hello.d(1:7)[error]: Expected identifier instead of ;
    // ddbc/source/ddbc/drivers/mysqlddbc.d(1273:35)[warn]: Line is longer than 120 characters
    private Optional<Problem> parseProblem(final String lint, final PsiFile file) {
        LOG.debug(lint);

        final Pattern p = Pattern.compile("\\w+\\.d\\((\\d+):(\\d+)\\)\\[(\\w+)\\]:(.+)");
        final Matcher m = p.matcher(lint);

        if (m.find()) {
            final int line = Integer.parseInt(m.group(1));
            final int column = Integer.parseInt(m.group(2));
            final TextRange range = LinterHelper.calculateTextRange(file, line, column);
            if (range == null) {
                LOG.warn("Unable to calculate text range for lint problem: " + lint);
                return Optional.empty();
            }
            final String severity = m.group(3);
            final String message = StringUtil.trim(m.group(4));

            final Problem problem = new Problem(range, message, severity);
            return Optional.of(problem);
        }
        return Optional.empty();
    }

    public static class Problem implements DProblem {
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
