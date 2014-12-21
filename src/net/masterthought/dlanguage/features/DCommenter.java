package net.masterthought.dlanguage.features;

import com.intellij.lang.CodeDocumentationAwareCommenter;
import com.intellij.psi.PsiComment;
import com.intellij.psi.tree.IElementType;
import net.masterthought.dlanguage.psi.DLanguageTokenType;
import org.jetbrains.annotations.Nullable;


public class DCommenter implements CodeDocumentationAwareCommenter {
    public String getLineCommentPrefix() {
        return "//";
    }

    public String getBlockCommentPrefix() {
        return "/*";
    }

    public String getBlockCommentSuffix() {
        return "*/";
    }

    public String getCommentedBlockCommentPrefix() {
        return null;
    }

    public String getCommentedBlockCommentSuffix() {
        return null;
    }

    @Nullable
    public IElementType getLineCommentTokenType() {
        return DLanguageTokenType.LINE_COMMENT;
    }

    @Nullable
    public IElementType getBlockCommentTokenType() {
        return DLanguageTokenType.BLOCK_COMMENT;
    }

    public String getDocumentationCommentPrefix() {
        return "/**";
    }

    public String getDocumentationCommentLinePrefix() {
        return "*";
    }

    public String getDocumentationCommentSuffix() {
        return "*/";
    }

    public boolean isDocumentationComment(final PsiComment element) {
        return element.getTokenType() == DLanguageTokenType.DOC_LINE_COMMENT ||
                element.getTokenType() == DLanguageTokenType.DOC_COMMENT_NEST ||
                element.getTokenType() == DLanguageTokenType.DOC_COMMENT;
    }

    @Nullable
    public IElementType getDocumentationCommentTokenType() {
        return DLanguageTokenType.DOC_LINE_COMMENT;
    }
}
