package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageImportBindings;
import net.masterthought.dlanguage.psi.DLanguageImportDeclaration;
import net.masterthought.dlanguage.psi.DLanguageSingleImport;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.masterthought.dlanguage.psi.DLanguageTypes.*;


public class DLanguageImportDeclarationImpl extends ASTWrapperPsiElement implements DLanguageImportDeclaration {
    public DLanguageImportDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitImportDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public PsiElement getKW_IMPORT() {
        return findChildByType(KW_IMPORT);
    }

    @NotNull
    public List<DLanguageSingleImport> getSingleImports() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageSingleImport.class);
    }

    @Nullable
    public DLanguageImportBindings getImportBindings() {
        return PsiTreeUtil.getChildOfType(this, DLanguageImportBindings.class);
    }

    @NotNull
    public List<PsiElement> getOP_COMMAs() {
        return findChildrenByType(OP_COMMA);
    }

    @Nullable
    public PsiElement getOP_SCOLON() {
        return findChildByType(OP_SCOLON);
    }

}
