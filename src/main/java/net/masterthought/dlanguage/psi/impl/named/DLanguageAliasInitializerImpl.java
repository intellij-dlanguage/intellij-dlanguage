package net.masterthought.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import net.masterthought.dlanguage.resolve.ScopeProcessorImpl;
import net.masterthought.dlanguage.stubs.DLanguageAliasInitializerStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.masterthought.dlanguage.psi.DLanguageTypes.OP_COMMA;

public class DLanguageAliasInitializerImpl extends DNamedStubbedPsiElementBase<DLanguageAliasInitializerStub> implements DLanguageAliasInitializer {
//todo doesn't cover all alias declarations possible
    public DLanguageAliasInitializerImpl(DLanguageAliasInitializerStub stub, IStubElementType type) {
        super(stub, type);
    }

    public DLanguageAliasInitializerImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitAliasInitializer(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public DLanguageIdentifier getIdentifier() {
        return PsiTreeUtil.getStubChildOfType(this, DLanguageIdentifier.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_EQ() {
        return findChildByType(OP_COMMA);
    }

    @NotNull
    @Override
    public List<DLanguageStorageClass> getStorageClasss() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageStorageClass.class);
    }

    @Override
    @Nullable
    public DLanguageTemplateParameters getTemplateParameters() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTemplateParameters.class);
    }

    @Override
    @Nullable
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @Nullable
    public DLanguageIdentifier getNameIdentifier() {
        return getIdentifier();
    }

    public boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }

}
