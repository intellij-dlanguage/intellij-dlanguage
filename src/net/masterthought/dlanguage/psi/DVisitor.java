package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import net.masterthought.dlanguage.lexer.PropertyImpl;
import net.masterthought.dlanguage.psi.interfaces.DModuleDeclaration;
import org.jetbrains.annotations.NotNull;

public class DVisitor extends PsiElementVisitor {

    public void visitProperty(@NotNull PropertyImpl o) {
        visitPsiElement(o);
    }

    public void visitPsiElement(@NotNull PsiElement o) {
        visitElement(o);
    }

    public void visitModuleDeclaration(@NotNull DModuleDeclaration o) {
        visitPsiElement(o);
    }
}
