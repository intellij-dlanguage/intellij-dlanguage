package io.github.intellij.dlanguage.highlighting.annotation.external;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiFile;
import com.intellij.util.containers.ContainerUtil;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.github.intellij.dlanguage.highlighting.annotation.DProblem;
import io.github.intellij.dlanguage.tools.dub.DubProcessListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Uses dub (with the default compiler) to check for compile errors periodically
 */
public class CompileCheck implements DlangLinter {
    private static final Logger LOG = Logger.getInstance(CompileCheck.class);

    @NotNull
    public DProblem[] checkFileSyntax(@NotNull final PsiFile file) {
        final String dubPath = ToolKey.DUB_KEY.getPath();
        if (StringUtil.isEmpty(dubPath)) return new DProblem[] {};

        final String result = processFile(file, dubPath);

        if(StringUtil.isNotEmpty(result)) {
            return findProblems(result, file).toArray(new DProblem[] {});
        }
        return new DProblem[] {};
    }

    @NotNull
    private String processFile(final PsiFile file, final String dubPath) {
        final GeneralCommandLine cmd = new GeneralCommandLine()
            .withWorkDirectory(file.getProject().getBasePath())
            .withExePath(dubPath)
            .withEnvironment("DFLAGS", "-o-")
            .withParameters("build", "--combined", "-q");

        try {
            final DubProcessListener listener = new DubProcessListener();
            final OSProcessHandler process = new OSProcessHandler(cmd.createProcess(), cmd.getCommandLineString());
            process.addProcessListener(listener);
            process.startNotify();
            process.waitFor();
            return listener.getOutput();
        } catch (final ExecutionException e) {
            LOG.warn("There was a problem running 'dub build --combined -q'", e);
        }
        return "";
    }


    @NotNull
    private List<DProblem> findProblems(final String stdout, final PsiFile file) {
        final List<String> lints = StringUtil.split(stdout, "\n");

        final List<DProblem> problems = new ArrayList<>();

        for (final String lint : lints) {
            ContainerUtil.addIfNotNull(problems, parseProblem(lint, file));
        }
        return problems;
    }

    @Nullable
    private CompilerProblem parseProblem(final String lint, final PsiFile file) {
        LOG.debug(lint);

        // Example DUB error:
        // src/hello.d(3,1): Error: only one main allowed
        final Pattern p = Pattern.compile("([\\w\\\\/]+\\.d)\\((\\d+),(\\d+)\\):\\s(\\w+):(.+)");
        final Matcher m = p.matcher(lint);

        String sourceFile = "";
        String message = "";
        int line = 0;
        int column = 0;
        String severity = "";
        boolean hasMatch = false;

        while (m.find()) {
            hasMatch = true;
            sourceFile = m.group(1);
            line = Integer.parseInt(m.group(2));
            column = Integer.parseInt(m.group(3));
            severity = m.group(4);
            message = m.group(5);
        }

        if (hasMatch && isSameFile(file, sourceFile)) {
            final TextRange range = LinterHelper.calculateTextRange(file, line, column);
            if(range != null) {
                return new CompilerProblem(range, message, severity);
            }
        }
        return null;
    }

    private boolean isSameFile(final PsiFile file, final String relativeOtherFilePath) {
        final String filePath = file.getVirtualFile().getPath();
        final String unixRelativeOtherFilePath = relativeOtherFilePath.replace('\\', '/');
        return filePath.endsWith(unixRelativeOtherFilePath);
    }

    public static class CompilerProblem implements DProblem {
        public final String severity;
        public final TextRange range;
        public final String message;

        public CompilerProblem(@NotNull final TextRange range, @NotNull final String message, @NotNull final String severity) {
            this.range = range;
            this.severity = severity;
            this.message = message;
        }

        @Override
        public void createAnnotations(@NotNull final PsiFile file, @NotNull final AnnotationHolder holder) {
            if (severity.equals("Error")) {
                holder.newAnnotation(HighlightSeverity.ERROR, message).range(range).create();
            } else {
                holder.newAnnotation(HighlightSeverity.WARNING, message).range(range).create();
            }
        }
    }
}
