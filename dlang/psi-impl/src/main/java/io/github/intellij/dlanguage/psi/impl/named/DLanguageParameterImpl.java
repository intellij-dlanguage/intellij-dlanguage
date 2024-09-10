package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAssignExpression;
import io.github.intellij.dlanguage.psi.DLanguageParameterAttribute;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.DLanguageTypeSuffix;
import io.github.intellij.dlanguage.psi.named.DLanguageParameter;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.stubs.DLanguageParameterStub;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DLanguageParameterImpl extends
    DNamedStubbedPsiElementBase<DLanguageParameterStub> implements DLanguageParameter {

    public DLanguageParameterImpl(final DLanguageParameterStub stub, final IStubElementType type) {
        super(stub, type);
    }

    public DLanguageParameterImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DlangVisitor visitor) {
        visitor.visitDNamedElement(this);
        visitor.visitParameter(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
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
        return findChildByType(DlangTypes.OP_EQ);
    }

    @Nullable
    @Override
    public PsiElement getKW_IMMUTABLE() {
        return findChildByType(DlangTypes.KW_IMMUTABLE);
    }
    @Nullable
    @Override
    public PsiElement getKW_SHARED() {
        return findChildByType(DlangTypes.KW_SHARED);
    }
    @Nullable
    @Override
    public PsiElement getKW_FINAL() {
        return findChildByType(DlangTypes.KW_FINAL);
    }
    @Nullable
    @Override
    public PsiElement getKW_CONST() {
        return findChildByType(DlangTypes.KW_CONST);
    }
    @Nullable
    @Override
    public PsiElement getKW_INOUT() {
        return findChildByType(DlangTypes.KW_INOUT);
    }
    @Nullable
    @Override
    public PsiElement getKW_IN() {
        return findChildByType(DlangTypes.KW_IN);
    }
    @Nullable
    @Override
    public PsiElement getKW_LAZY() {
        return findChildByType(DlangTypes.KW_LAZY);
    }
    @Nullable
    @Override
    public PsiElement getKW_OUT() {
        return findChildByType(DlangTypes.KW_OUT);
    }
    @Nullable
    @Override
    public PsiElement getKW_REF() {
        return findChildByType(DlangTypes.KW_REF);
    }
    @Nullable
    @Override
    public PsiElement getKW_SCOPE() {
        return findChildByType(DlangTypes.KW_SCOPE);
    }
    @Nullable
    @Override
    public PsiElement getKW_AUTO() {
        return findChildByType(DlangTypes.KW_AUTO);
    }
    @Nullable
    @Override
    public PsiElement getKW_RETURN() {
        return findChildByType(DlangTypes.KW_RETURN);
    }

    @Override
    @Nullable
    public PsiElement getIdentifier() {
        return findChildByType(DlangTypes.ID);
    }

    @NotNull
    @Override
    public List<DLanguageTypeSuffix> getTypeSuffixs() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageTypeSuffix.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_TRIPLEDOT() {
        return findChildByType(DlangTypes.OP_TRIPLEDOT);
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
    public PsiElement getNameIdentifier() {
        if (getIdentifier() != null) {
            return getIdentifier();
        }
        if (getType() != null) {
            if (getType().getBasicType() != null) {
                if (getType().getBasicType().getQualifiedIdentifier() != null) {
                    return getType().getBasicType().getQualifiedIdentifier().getIdentifier();
                }
            }
        }
        return null;
    }
}
