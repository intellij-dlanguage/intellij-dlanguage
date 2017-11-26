package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.stubs.DlangFunctionDeclarationStub;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import io.github.intellij.dlanguage.stubs.DlangFunctionDeclarationStub;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by francis on 7/14/2017.
 */
public class DLanguageFunctionDeclarationImpl extends DNamedStubbedPsiElementBase<DlangFunctionDeclarationStub> implements DLanguageFunctionDeclaration {

    public DLanguageFunctionDeclarationImpl(@NotNull final DlangFunctionDeclarationStub stub, final IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DLanguageFunctionDeclarationImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitFunctionDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    @Override
    public DlangIdentifier getNameIdentifier() {
        return getIdentifier();
    }

    @Nullable
    @Override
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @Nullable
    @Override
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
    }

    @Nullable
    @Override
    public DLanguageTemplateParameters getTemplateParameters() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTemplateParameters.class);
    }

    @Nullable
    @Override
    public DLanguageParameters getParameters() {
        return PsiTreeUtil.getChildOfType(this, DLanguageParameters.class);
    }

    @Nullable
    @Override
    public DLanguageConstraint getConstraint() {
        return PsiTreeUtil.getChildOfType(this, DLanguageConstraint.class);
    }

    @Nullable
    @Override
    public DLanguageFunctionBody getFunctionBody() {
        return PsiTreeUtil.getChildOfType(this, DLanguageFunctionBody.class);
    }

    @Override
    public List<DLanguageMemberFunctionAttribute> getMemberFunctionAttributes() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageMemberFunctionAttribute.class);
    }

    @Override
    public boolean processDeclarations(@NotNull final PsiScopeProcessor processor, @NotNull final ResolveState state, final PsiElement lastParent, @NotNull final PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this,processor, state, lastParent, place);
    }
}
