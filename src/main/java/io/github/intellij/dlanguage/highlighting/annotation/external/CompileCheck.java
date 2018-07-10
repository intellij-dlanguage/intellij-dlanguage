package io.github.intellij.dlanguage.highlighting.annotation.external;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.util.containers.ContainerUtil;
import io.github.intellij.dlanguage.highlighting.annotation.DAnnotationHolder;
import io.github.intellij.dlanguage.highlighting.annotation.Problems;
import io.github.intellij.dlanguage.settings.ToolKey;
import io.github.intellij.dlanguage.highlighting.annotation.DProblem;
import io.github.intellij.dlanguage.tools.dub.DubProcessListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompileCheck {
    private static final Logger LOG = Logger.getInstance(CompileCheck.class);

    @NotNull
    public Problems checkFileSyntax(@NotNull final PsiFile file) {
        final String dubPath = ToolKey.DUB_KEY.getPath();
        if (dubPath == null) return new Problems();

        final String result = processFile(file, dubPath);
        return findProblems(result, file);
    }

    @NotNull
    private String processFile(final PsiFile file, final String dubPath) {
        final GeneralCommandLine cmd = new GeneralCommandLine()
            .withWorkDirectory(file.getProject().getBasePath())
            .withExePath(dubPath)
            .withEnvironment("DFLAGS", "-o-")
            .withParameters("build", "--combined", "-q");

        try {
            final String dubCommand = cmd.getCommandLineString();
            final DubProcessListener listener = new DubProcessListener();
            final OSProcessHandler process = new OSProcessHandler(cmd.createProcess(), dubCommand);
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
    private Problems findProblems(final String stdout, final PsiFile file) {
        final List<String> lints = StringUtil.split(stdout, "\n");
        final Problems problems = new Problems();
        for (final String lint : lints) {
            ContainerUtil.addIfNotNull(problems, parseProblem(lint, file));
        }
        return problems;
    }

    private int getDocumentLineCount(final Document document) {
        try {
            final int lineCount = document.getLineCount();
            return lineCount == 0 ? 1 : lineCount;
        } catch (final NullPointerException e) {
            return 1;
        }
    }

    private int getLineStartOffset(final Document document, final int line) {
        try {
            return document.getLineStartOffset(line);
        } catch (final Exception e) {
            return 1;
        }
    }

    private int getLineEndOffset(final Document document, final int line) {
        try {
            return document.getLineEndOffset(line);
        } catch (final Exception e) {
            return 1;
        }
    }

    private int getValidLineNumber(int line, final Document document) {
        final int lineCount = getDocumentLineCount(document);
        line = line - 1;
        if (line <= 0) {
            line = 1;
        } else if (line >= lineCount) {
            line = lineCount - 1;
        }
        return line;
    }

    private int getOffsetStart(final PsiFile file, int line, final int column) {
        final Document document = PsiDocumentManager.getInstance(file.getProject()).getDocument(file);
        line = getValidLineNumber(line, document);
        return getLineStartOffset(document, line) + column;
    }

    private int getOffsetEnd(final PsiFile file, int line) {
        final Document document = PsiDocumentManager.getInstance(file.getProject()).getDocument(file);
        line = getValidLineNumber(line, document);
        return getLineEndOffset(document, line);
    }

    private TextRange calculateTextRange(final PsiFile file, final int line, final int column) {
        final int startOffset = getOffsetStart(file, line, column);
        final int endOffset = getOffsetEnd(file, line);
        try {
            class IgnoresInvalidTextRange extends TextRange {
                private IgnoresInvalidTextRange(final int startOffset, final int endOffset) {
                    super(startOffset, endOffset, false);
                }
            }
            return new IgnoresInvalidTextRange(startOffset, endOffset);
        } catch (final Throwable e) {
            if (e.getMessage().contains("Invalid range")) {
                //do nothing.
                return null;
            } else throw e;
        }
    }

    @Nullable
    private Problem parseProblem(final String lint, final PsiFile file) {
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
            line = Integer.valueOf(m.group(2));
            column = Integer.valueOf(m.group(3)) - 1;
            severity = m.group(4);
            message = m.group(5);
        }

        if (hasMatch && isSameFile(file, sourceFile)) {
            final TextRange range = calculateTextRange(file, line, column);
            return new Problem(range, message, severity);
        } else {
            return null;
        }
    }

    private boolean isSameFile(final PsiFile file, final String relativeOtherFilePath) {
        final String filePath = file.getVirtualFile().getPath();
        final String unixRelativeOtherFilePath = relativeOtherFilePath.replace('\\', '/');
        return filePath.endsWith(unixRelativeOtherFilePath);
    }

    public static class Problem extends DProblem {
        public final String severity;
        public final TextRange range;
        public final String message;

        public Problem(final TextRange range, final String message, final String severity) {
            this.range = range;
            this.severity = severity;
            this.message = message;
        }

        @Override
        public void createAnnotations(@NotNull final PsiFile file, @NotNull final DAnnotationHolder holder) {
            if (severity.equals("Error")) {
                holder.createErrorAnnotation(range, message);
            } else {
                holder.createWarningAnnotation(range, message);
            }
        }
    }
}
