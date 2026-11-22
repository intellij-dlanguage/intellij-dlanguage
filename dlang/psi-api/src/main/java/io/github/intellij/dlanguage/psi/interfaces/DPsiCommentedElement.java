package io.github.intellij.dlanguage.psi.interfaces;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.documentation.psi.DlangDocComment;

import java.util.List;

public interface DPsiCommentedElement extends PsiElement {

    List<DlangDocComment> getDocComments();
}
