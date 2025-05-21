package io.github.intellij.dlanguage.highlighting.annotation.external;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LinterHelper {

    /**
     * Take a line (convention: 1st line == 1) and a document and return the line number in convention where 1st line is 0.
     * If the line is out of document, then it will be put at the border of it.
     *
     * @param line The line number where the convention is 1st line == 1
     * @param document the document where belongs the line
     * @return A valid (in document, good convention) line number for this document
     */
    private static int getValidLineNumber(int line, final Document document) {
        final int lineCount = document.getLineCount();
        line = line - 1;
        if (line >= lineCount) {
            line = lineCount - 1;
        }
        if (line <= 0) {
            line = 0;
        }
        return line;
    }

    private static int getValidColumnNumber(int column) {
        column = column - 1;
        if (column < 0)
            column = 0;
        return column;
    }

    /**
     * Take the file, the line number and the column number of the beginning of a problem and return a range for this problem
     * @param file The file the problem belongs to
     * @param line The line number of the begining of the problem.
     *             The line number is expressed in format where the 1st line of the file is 1.
     * @param column The column number of the begining of the problem.
     *               The column number is expressed in format where the 1st line of the file is 1.
     * @return The TextRange for this problem or null if it was not able to compute it
     */
    @Nullable
    public static TextRange calculateTextRange(@NotNull final PsiFile file, final int line, final int column) {
        ProgressManager.checkCanceled();
        final Document document = PsiDocumentManager.getInstance(file.getProject()).getDocument(file);
        if (document == null)
            return null;
        final int validLine = getValidLineNumber(line, document);
        final int validColumn = getValidColumnNumber(column);
        int startOffset = StringUtil.lineColToOffset(document.getImmutableCharSequence(), validLine, validColumn);
        final int endOffset = document.getLineEndOffset(validLine);
        if (startOffset > endOffset)
            startOffset = endOffset;
        try {
            return new TextRange(startOffset, endOffset);
        } catch (final IllegalArgumentException e) {
            if (e.getMessage().contains("Invalid range")) {
                //do nothing.
                return null;
            } else throw e;
        }
    }
}
