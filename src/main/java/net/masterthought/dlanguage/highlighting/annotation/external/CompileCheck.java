package net.masterthought.dlanguage.highlighting.annotation.external;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.util.containers.ContainerUtil;
import net.masterthought.dlanguage.highlighting.annotation.DAnnotationHolder;
import net.masterthought.dlanguage.highlighting.annotation.DProblem;
import net.masterthought.dlanguage.highlighting.annotation.Problems;
import net.masterthought.dlanguage.settings.ToolKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompileCheck {

    public Problems checkFileSyntax(@NotNull PsiFile file) {
        final String dubPath = ToolKey.DUB_KEY.getPath(file.getProject());
        if (dubPath == null) return new Problems();

        String result = processFile(file, dubPath);
        return findProblems(result, file);
    }

    private String processFile(PsiFile file, String dubPath) {
        final String workingDirectory = file.getProject().getBasePath();

        GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.setWorkDirectory(workingDirectory);
        commandLine.setExePath(dubPath);
        ParametersList parametersList = commandLine.getParametersList();
        parametersList.addParametersString("build");
        parametersList.addParametersString("--combined");
        parametersList.addParametersString("-q");

        final StringBuilder builder = new StringBuilder();
        try {
            final OSProcessHandler process = new OSProcessHandler(commandLine.createProcess(), commandLine.getCommandLineString());
            process.addProcessListener(new ProcessAdapter() {
                @Override
                public void onTextAvailable(ProcessEvent event, Key outputType) {
                    builder.append(event.getText());
                }
            });

            process.startNotify();
            process.waitFor();

        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }


    @NotNull
    private Problems findProblems(String stdout, PsiFile file) {
        final List<String> lints = StringUtil.split(stdout, "\n");
        Problems problems = new Problems();
        for (String lint : lints) {
            ContainerUtil.addIfNotNull(problems, parseProblem(lint, file));
        }
        return problems;
    }

    private int getDocumentLineCount(Document document) {
        try {
            int lineCount = document.getLineCount();
            return lineCount == 0 ? 1 : lineCount;
        } catch (NullPointerException e) {
            return 1;
        }
    }

    private int getLineStartOffset(Document document, int line) {
        try {
            return document.getLineStartOffset(line);
        } catch (Exception e) {
            return 1;
        }
    }

    private int getLineEndOffset(Document document, int line) {
        try {
            return document.getLineEndOffset(line);
        } catch (Exception e) {
            return 1;
        }
    }

    private int getValidLineNumber(int line, Document document) {
        int lineCount = getDocumentLineCount(document);
        line = line - 1;
        if (line <= 0) {
            line = 1;
        } else if (line >= lineCount) {
            line = lineCount - 1;
        }
        return line;
    }

    private int getOffsetStart(final PsiFile file, int line, int column) {
        Document document = PsiDocumentManager.getInstance(file.getProject()).getDocument(file);
        line = getValidLineNumber(line, document);
        return getLineStartOffset(document, line) + column;
    }

    private int getOffsetEnd(final PsiFile file, int line) {
        Document document = PsiDocumentManager.getInstance(file.getProject()).getDocument(file);
        line = getValidLineNumber(line, document);
        return getLineEndOffset(document, line);
    }

    private TextRange calculateTextRange(PsiFile file, int line, int column) {
        final int startOffset = getOffsetStart(file, line, column);
        final int endOffset = getOffsetEnd(file, line);
        return new TextRange(startOffset, endOffset);
    }

    @Nullable
    private Problem parseProblem(String lint, PsiFile file) {
        // Example DUB error:
        // src/hello.d(3,1): Error: only one main allowed
        Pattern p = Pattern.compile("([\\w\\\\/]+\\.d)\\((\\d+),(\\d+)\\):\\s(\\w+):(.+)");
        Matcher m = p.matcher(lint);

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
            TextRange range = calculateTextRange(file, line, column);
            return new Problem(range, message, severity);
        } else {
            return null;
        }
    }

    private boolean isSameFile(PsiFile file, String relativeOtherFilePath) {
        String filePath = file.getVirtualFile().getPath();
        String unixRelativeOtherFilePath = relativeOtherFilePath.replace('\\', '/');
        return filePath.endsWith(unixRelativeOtherFilePath);
    }

    public static class Problem extends DProblem {
        public String severity;
        public TextRange range;
        public String message;

        public Problem(TextRange range, String message, String severity) {
            this.range = range;
            this.severity = severity;
            this.message = message;
        }

        @Override
        public void createAnnotations(@NotNull PsiFile file, @NotNull DAnnotationHolder holder) {
            if (severity.equals("Error")) {
                holder.createErrorAnnotation(range, message);
            } else {
                holder.createWarningAnnotation(range, message);
            }
        }
    }

}
