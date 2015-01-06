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

public class DScanner {

    public Problems checkFileSyntax(@NotNull PsiFile file) {
        final String dscannerPath = ToolKey.DSCANNER_KEY.getPath(file.getProject());
        if (dscannerPath == null) return new Problems();

        String result = processFile(file, dscannerPath);
        return findProblems(result, file);
    }

    private String processFile(PsiFile file, String dscannerPath) {
        final String filePath = file.getVirtualFile().getCanonicalPath();
        final String workingDirectory = file.getProject().getBasePath();

        GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.setWorkDirectory(workingDirectory);
        commandLine.setExePath(dscannerPath);
        ParametersList parametersList = commandLine.getParametersList();
        parametersList.addParametersString("-S");
        parametersList.addParametersString(filePath);
        final StringBuilder builder = new StringBuilder();
        try {
            OSProcessHandler process = new OSProcessHandler(commandLine.createProcess());
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
    public static Problems findProblems(String stdout, PsiFile file) {
        final List<String> lints = StringUtil.split(stdout, "\n");
        Problems problems = new Problems();
        for (String lint : lints) {
            ContainerUtil.addIfNotNull(problems, parseProblem(lint, file));
        }
        return problems;
    }

    public static int getOffsetStart(final PsiFile file, int startLine, int startColumn) {
        Document document = PsiDocumentManager.getInstance(file.getProject()).getDocument(file);
        int line = getValidLineNumber(startLine, document);
        return StringUtil.lineColToOffset(file.getText(), line, startColumn - 1);
    }

    private static int getLineEndOffset(Document document, int line) {
        try {
            return document.getLineEndOffset(line);
        } catch (NullPointerException e) {
            return 1;
        }
    }

    private static int getDocumentLineCount(Document document) {
        try {
            int lineCount = document.getLineCount();
            return lineCount == 0 ? 1 : lineCount;
        } catch (NullPointerException e) {
            return 1;
        }
    }

    private static int getValidLineNumber(int line, Document document) {
        int lineCount = getDocumentLineCount(document);
        line = line - 1;
        if (line <= 0) {
            line = 1;
        } else if (line >= lineCount) {
            line = lineCount - 1;
        }
        return line;
    }

    public static int getOffsetEndFallback(final PsiFile file, int line) {
        Document document = PsiDocumentManager.getInstance(file.getProject()).getDocument(file);
        line = getValidLineNumber(line, document);
        return getLineEndOffset(document, line);
    }

    public static int getOffsetEnd(PsiFile file, int offsetStart, int line) {
        try {
            String fileText = file.getText();
            int width = 0;
            while (offsetStart + width < fileText.length()) {
                final char c = fileText.charAt(offsetStart + width);
                if (StringUtil.isLineBreak(c)) {
                    break;
                }
                ++width;
            }
            return offsetStart + width;
        } catch (Exception e) {
            return getOffsetEndFallback(file, line);
        }
    }

//    public static int getOffsetEnd(PsiFile file, int offsetStart) {
//        Document document = PsiDocumentManager.getInstance(file.getProject()).getDocument(file);
//        document.g
//        PsiElement element = file.getViewProvider().findElementAt(offsetStart);
//        return element.getTextOffset();
//    }

    private static TextRange calculateTextRange(PsiFile file, int line, int column) {
        final int startOffset = getOffsetStart(file, line, column);
        final int endOffset = getOffsetEnd(file, startOffset, line);
        return new TextRange(startOffset, endOffset);
    }

    // hello.d(1:7)[error]: Expected identifier instead of ;
    @Nullable
    public static Problem parseProblem(String lint, PsiFile file) {
        Pattern p = Pattern.compile("\\w+\\.d\\((\\d+):(\\d+)\\)\\[(\\w+)\\]:(.+)");
        Matcher m = p.matcher(lint);

        String message = "";
        int line = 0;
        int column = 0;
        String severity = "";

        while (m.find()) {
            line = Integer.valueOf(m.group(1));
            column = Integer.valueOf(m.group(2));
            severity = m.group(3);
            message = m.group(4);
        }

        TextRange range = calculateTextRange(file, line, column);

        return new Problem(range, message, severity);
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
            if (severity.equals("error")) {
                holder.createErrorAnnotation(range, message);
            } else if (message.contains("undocumented")) {
                holder.createInfoAnnotation(range, message);
            } else {
                holder.createWarningAnnotation(range, message);
            }
        }
    }

}
