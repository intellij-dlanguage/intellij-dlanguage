package io.github.intellij.dlanguage.psi.interfaces;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.documentation.psi.DlangDocComment;

import java.util.List;

/**
 * Created by francis on 3/19/2017.
 */
public interface Declaration extends PsiElement, DPsiCommentedElement {

    default List<DlangDocComment> getDocComments() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DlangDocComment.class);
    }
}
