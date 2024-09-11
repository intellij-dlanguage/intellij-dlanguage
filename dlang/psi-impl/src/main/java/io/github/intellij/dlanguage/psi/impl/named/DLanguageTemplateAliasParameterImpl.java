package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAssignExpression;
import io.github.intellij.dlanguage.psi.DLanguageTemplateAliasParameter;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.stubs.DLanguageTemplateAliasParameterStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;


public class DLanguageTemplateAliasParameterImpl extends
    DNamedStubbedPsiElementBase<DLanguageTemplateAliasParameterStub> implements DLanguageTemplateAliasParameter {

    public DLanguageTemplateAliasParameterImpl(ASTNode node) {
        super(node);
    }

    @Override
    public @Nullable PsiElement getNameIdentifier() {
        return getIdentifier();
    }

    public DLanguageTemplateAliasParameterImpl(DLanguageTemplateAliasParameterStub stub,
        IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitTemplateAliasParameter(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getKW_ALIAS() {
        return findChildByType(KW_ALIAS);
    }

    @Nullable
    public PsiElement getIdentifier() {
        return findChildByType(ID);
    }

    @NotNull
    public List<DLanguageType> getTypes() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageType.class);
    }

    @NotNull
    public List<DLanguageAssignExpression> getAssignExpressions() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAssignExpression.class);
    }

    @Nullable
    public PsiElement getOP_COLON() {
        return findChildByType(OP_COLON);
    }

    @Nullable
    public PsiElement getOP_EQ() {
        return findChildByType(OP_EQ);
    }

}
