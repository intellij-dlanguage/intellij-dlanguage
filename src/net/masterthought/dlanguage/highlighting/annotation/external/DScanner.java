package net.masterthought.dlanguage.highlighting.annotation.external;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiFile;
import com.intellij.util.containers.ContainerUtil;
import net.masterthought.dlanguage.highlighting.annotation.DAnnotationHolder;
import net.masterthought.dlanguage.highlighting.annotation.DProblem;
import net.masterthought.dlanguage.highlighting.annotation.Problems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DScanner {

//    private String DSCANNER_PATH = "/Users/kings/development/tools/Dscanner/bin/dscanner";
    private String DSCANNER_PATH = "/home/kings/development/projects/dlang/Dscanner/bin/dscanner";

    public Problems checkFileSyntax(@NotNull PsiFile file) {
        String result = processFile(file);
        return findProblems(result, file);
    }

    private String processFile(PsiFile file) {
        final String filePath = file.getVirtualFile().getCanonicalPath();
        final String workingDirectory = file.getProject().getBasePath();

        GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.setWorkDirectory(workingDirectory);
        commandLine.setExePath(DSCANNER_PATH);
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

    public static int getOffsetStart(final String fileText, int startLine, int startColumn) {
        return StringUtil.lineColToOffset(fileText, startLine - 1, startColumn - 1);
    }

    public static int getOffsetEnd(String fileText, int offsetStart) {
        int width = 0;
        while (offsetStart + width < fileText.length()) {
            final char c = fileText.charAt(offsetStart + width);
            if (StringUtil.isLineBreak(c)) {
                break;
            }
            ++width;
        }
        return offsetStart + width;
    }

    private static TextRange calculateTextRange(PsiFile file, int line, int column) {
        final int startOffset = getOffsetStart(file.getText(), line, column);
        final int endOffset = getOffsetEnd(file.getText(), startOffset);
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
