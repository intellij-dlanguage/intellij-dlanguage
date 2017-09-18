package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageArrayInitializer;
import net.masterthought.dlanguage.psi.DLanguageArrayMemberInitialization;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.masterthought.dlanguage.psi.DlangTypes.*;


public class DLanguageArrayInitializerImpl extends ASTWrapperPsiElement implements DLanguageArrayInitializer {
    public DLanguageArrayInitializerImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitArrayInitializer(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @NotNull
    public List<PsiElement> getOP_COMMAs() {
        return findChildrenByType(OP_COMMA);
    }

    @NotNull
    public List<DLanguageArrayMemberInitialization> getArrayMemberInitializations() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageArrayMemberInitialization.class);
    }

    @Nullable
    public PsiElement getOP_BRACKET_RIGHT() {
        return findChildByType(OP_BRACKET_RIGHT);
    }

    @Nullable
    public PsiElement getOP_BRACKET_LEFT() {
        return findChildByType(OP_BRACKET_LEFT);
    }

}
