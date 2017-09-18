package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import net.masterthought.dlanguage.psi.DLanguageString;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.masterthought.dlanguage.psi.DlangTypes.DOUBLE_QUOTED_STRING;


public class DLanguageStringImpl extends ASTWrapperPsiElement implements DLanguageString {
    public DLanguageStringImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitString(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @NotNull
    public List<PsiElement> getDOUBLE_QUOTED_STRINGs() {
        return findChildrenByType(DOUBLE_QUOTED_STRING);
    }

}
