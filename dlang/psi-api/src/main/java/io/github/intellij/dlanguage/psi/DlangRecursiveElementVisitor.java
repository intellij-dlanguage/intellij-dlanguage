package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiRecursiveVisitor;
import org.jetbrains.annotations.NotNull;

public class DlangRecursiveElementVisitor extends DlangVisitor implements PsiRecursiveVisitor {
    @Override
    public void visitElement(@NotNull PsiElement element) {
        element.acceptChildren(this);
    }
}
