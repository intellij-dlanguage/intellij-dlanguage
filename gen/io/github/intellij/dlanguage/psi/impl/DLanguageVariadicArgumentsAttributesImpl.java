package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.psi.DLanguageVariadicArgumentsAttribute;
import io.github.intellij.dlanguage.psi.DLanguageVariadicArgumentsAttributes;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_TRIPLEDOT;

public class DLanguageVariadicArgumentsAttributesImpl extends ASTWrapperPsiElement implements
    DLanguageVariadicArgumentsAttributes {

    public DLanguageVariadicArgumentsAttributesImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitVariadicArgumentsAttributes(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public List<PsiElement> getOP_TRIPLEDOTs() {
        return findChildrenByType(OP_TRIPLEDOT);
    }

    @Nullable
    public List<DLanguageVariadicArgumentsAttribute> getVariadicArgumentsAttributes() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageVariadicArgumentsAttribute.class);
    }
}
