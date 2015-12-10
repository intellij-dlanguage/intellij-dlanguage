package net.masterthought.dlanguage.features;

import com.intellij.lang.CodeDocumentationAwareCommenter;
import com.intellij.psi.PsiComment;
import com.intellij.psi.tree.IElementType;
import net.masterthought.dlanguage.psi.DLanguageTypes;
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
        return DLanguageTypes.LINE_COMMENT;
    }

    @Nullable
    public IElementType getBlockCommentTokenType() {
        return DLanguageTypes.BLOCK_COMMENT;
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
        return false;
//        return element.getTokenType() == DElementTypes.DOCCOMMENT_LINE ||
//                element.getTokenType() == DElementTypes.DOCCOMMENT_MULTI ||
//                element.getTokenType() == DElementTypes.DOCCOMMENT_NESTED;
    }

    @Nullable
    public IElementType getDocumentationCommentTokenType() {
        return DLanguageTypes.LINE_COMMENT;
    }
}
