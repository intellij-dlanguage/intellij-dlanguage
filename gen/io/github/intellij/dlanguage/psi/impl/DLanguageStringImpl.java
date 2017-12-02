package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.DOUBLE_QUOTED_STRING;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import io.github.intellij.dlanguage.psi.DLanguageString;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public class DLanguageStringImpl extends ASTWrapperPsiElement implements DLanguageString {

    public DLanguageStringImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitString(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @NotNull
    public List<PsiElement> getDOUBLE_QUOTED_STRINGs() {
        return findChildrenByType(DOUBLE_QUOTED_STRING);
    }

}
