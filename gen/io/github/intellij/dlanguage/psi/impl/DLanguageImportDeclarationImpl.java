package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_IMPORT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_COMMA;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_SCOLON;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageImportBindings;
import io.github.intellij.dlanguage.psi.DLanguageImportDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangSingleImport;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageImportDeclarationImpl extends ASTWrapperPsiElement implements
    DLanguageImportDeclaration {

    public DLanguageImportDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitImportDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getKW_IMPORT() {
        return findChildByType(KW_IMPORT);
    }

    @NotNull
    public List<DlangSingleImport> getSingleImports() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DlangSingleImport.class);
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

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor,
        @NotNull ResolveState state,
        PsiElement lastParent,
        @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE
            .processDeclarations(this, processor, state, lastParent, place);
    }
}
