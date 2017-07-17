package net.masterthought.dlanguage.highlighting.annotation.external;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessOutputTypes;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import net.masterthought.dlanguage.highlighting.annotation.DAnnotationHolder;
import net.masterthought.dlanguage.highlighting.annotation.DProblem;
import net.masterthought.dlanguage.highlighting.annotation.Problems;
import net.masterthought.dlanguage.settings.ToolKey;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DScanner {

    private static final Logger LOG = Logger.getInstance(DScanner.class);

    private static int getOffsetStart(final PsiFile file, int startLine, int startColumn) {
        Document document = PsiDocumentManager.getInstance(file.getProject()).getDocument(file);
        int line = getValidLineNumber(startLine, document);
        int offset = StringUtil.lineColToOffset(file.getText(), line, startColumn - 1);
        return offset <= 1 ? 1 : offset;
    }

    private static int getLineEndOffset(Document document, int line) {
        try {
            return document.getLineEndOffset(line);
        } catch (Exception e) {
            return 1;
        }
    }

    private static int getDocumentLineCount(Document document) {
        try {
            int lineCount = document.getLineCount();
            return lineCount == 0 ? 1 : lineCount;
        } catch (Exception e) {
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

    private static int getOffsetEndFallback(final PsiFile file, int line) {
        Document document = PsiDocumentManager.getInstance(file.getProject()).getDocument(file);
        line = getValidLineNumber(line, document);
        return getLineEndOffset(document, line);
    }

    private static int getOffsetEnd(PsiFile file, int offsetStart, int line) {
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

    private static TextRange calculateTextRange(PsiFile file, int line, int column) {
        final int startOffset = getOffsetStart(file, line, column);
        final int endOffset = getOffsetEnd(file, startOffset, line);
        return new TextRange(startOffset, endOffset);
    }

    Problems checkFileSyntax(@NotNull PsiFile file) {
        final String dscannerPath = ToolKey.DSCANNER_KEY.getPath(file.getProject());
        if (dscannerPath == null) return new Problems();

        final Problems problems = new Problems();
        problems.addAllNotNull(processFile(file, dscannerPath));
        return problems;
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
                public void onTextAvailable(ProcessEvent event, Key outputType) {
                    // we don't care about "system" or "stderr"
                    if (ProcessOutputTypes.STDOUT.equals(outputType)) {
                        parseProblem(event.getText(), file)
                            .ifPresent(problems::add);
                    } else if (ProcessOutputTypes.STDERR.equals(outputType)) {
                        LOG.error(event.getText());
                    }
                }
            });

            process.startNotify();
            process.waitFor();

            final Integer exitCode = process.getExitCode(); // 0 or 1 depending on whether DScanner found problems

            if (Integer.valueOf(1).equals(exitCode)) {
                LOG.debug("DScanner found lint problems");
            }
        } catch (final ExecutionException e) {
            LOG.error("There was a problem running DScanner", e);
        }
        return problems;
    }

    // hello.d(1:7)[error]: Expected identifier instead of ;
    private Optional<Problem> parseProblem(String lint, PsiFile file) {
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
        public void createAnnotations(@NotNull PsiFile file, @NotNull DAnnotationHolder holder) {
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
