package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageAutoDeclaration;
import net.masterthought.dlanguage.psi.DLanguageAutoDeclarationPart;
import net.masterthought.dlanguage.psi.DLanguageStorageClass;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_COMMA;
import static net.masterthought.dlanguage.psi.DlangTypes.OP_SCOLON;


public class DLanguageAutoDeclarationImpl extends ASTWrapperPsiElement implements DLanguageAutoDeclaration {
    public DLanguageAutoDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAutoDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageStorageClass getStorageClass() {
        return PsiTreeUtil.getChildOfType(this, DLanguageStorageClass.class);
    }

    @NotNull
    public List<PsiElement> getOP_COMMAs() {
        return findChildrenByType(OP_COMMA);
    }

    @Nullable
    public PsiElement getOP_SCOLON() {
        return findChildByType(OP_SCOLON);
    }

    @NotNull
    public List<DLanguageAutoDeclarationPart> getAutoDeclarationParts() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAutoDeclarationPart.class);
    }
}
