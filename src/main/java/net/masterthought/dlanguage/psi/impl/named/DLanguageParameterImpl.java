package net.masterthought.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import net.masterthought.dlanguage.stubs.DlangParameterStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_EQ;
import static net.masterthought.dlanguage.psi.DlangTypes.OP_TRIPLEDOT;

public class DLanguageParameterImpl extends DNamedStubbedPsiElementBase<DlangParameterStub> implements DLanguageParameter {

    public DLanguageParameterImpl(final DlangParameterStub stub, final IStubElementType type) {
        super(stub, type);
    }

    public DLanguageParameterImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DLanguageVisitor visitor) {
        visitor.visitParameter(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public DLanguageAssignExpression getAssignExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAssignExpression.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_EQ() {
        return findChildByType(OP_EQ);
    }

    @Override
    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getStubChildOfType(this, DlangIdentifier.class);
    }

    @NotNull
    @Override
    public List<DLanguageTypeSuffix> getTypeSuffixs() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageTypeSuffix.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_TRIPLEDOT() {
        return findChildByType(OP_TRIPLEDOT);
    }

    @NotNull
    @Override
    public List<DLanguageParameterAttribute> getParameterAttributes() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageParameterAttribute.class);
    }

    @Override
    @Nullable
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

//    public String getFullName() {
//        return DPsiImplUtil.getFullName(this);
//    }

    @Nullable
    public DlangIdentifier getNameIdentifier() {
        return getIdentifier();
    }
}
