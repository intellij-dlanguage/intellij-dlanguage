package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.stubs.DlangIfConditionStub;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by francis on 7/14/2017.
 */
public class DLanguageIfConditionImpl extends DNamedStubbedPsiElementBase<DlangIfConditionStub> implements DLanguageIfCondition {
    public DLanguageIfConditionImpl(@NotNull final DlangIfConditionStub stub, final IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DLanguageIfConditionImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DlangVisitor visitor) {visitor.visitDNamedElement(this);
        visitor.visitIfCondition(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    @Override
    public PsiElement getNameIdentifier() {
        return getIdentifier();
    }

    @Nullable
    @Override
    public PsiElement getIdentifier() {
        return findChildByType(DlangTypes.ID);
    }

    @NotNull
    @Override
    public List<DLanguageAssignExpression> getAssignExpressions() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAssignExpression.class);
    }

    @Override
    @NotNull
    public List<PsiElement> getOP_COMMAs() {
        return findChildrenByType(DlangTypes.OP_COMMA);
    }

    @Nullable
    @Override
    public PsiElement getKW_AUTO() {
        return findChildByType(DlangTypes.KW_AUTO);
    }

    @Nullable
    @Override
    public PsiElement getKW_SCOPE() {
        return findChildByType(DlangTypes.KW_SCOPE);
    }

    @Nullable
    @Override
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_EQ() {
        return findChildByType(DlangTypes.OP_EQ);
    }
}
