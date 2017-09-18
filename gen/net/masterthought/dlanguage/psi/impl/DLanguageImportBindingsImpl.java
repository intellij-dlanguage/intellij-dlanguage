package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageImportBind;
import net.masterthought.dlanguage.psi.DLanguageImportBindings;
import net.masterthought.dlanguage.psi.DlangSingleImport;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_COLON;
import static net.masterthought.dlanguage.psi.DlangTypes.OP_COMMA;


public class DLanguageImportBindingsImpl extends ASTWrapperPsiElement implements DLanguageImportBindings {
    public DLanguageImportBindingsImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitImportBindings(this);
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
    public List<DLanguageImportBind> getImportBinds() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageImportBind.class);
    }

    @Nullable
    public DlangSingleImport getSingleImport() {
        return PsiTreeUtil.getChildOfType(this, DlangSingleImport.class);
    }

    @Nullable
    public PsiElement getOP_COLON() {
        return findChildByType(OP_COLON);
    }

}
