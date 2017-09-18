package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageDestructor;
import net.masterthought.dlanguage.psi.DLanguageFunctionBody;
import net.masterthought.dlanguage.psi.DLanguageMemberFunctionAttribute;
import net.masterthought.dlanguage.psi.DlangVisitor;
import net.masterthought.dlanguage.stubs.DlangDestructorStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.masterthought.dlanguage.psi.DlangTypes.*;


public class DLanguageDestructorImpl extends DStubbedPsiElementBase<DlangDestructorStub> implements DLanguageDestructor {
    public DLanguageDestructorImpl(final DlangDestructorStub stub, final IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DLanguageDestructorImpl(final ASTNode node) {
        super(node);

    }

    public void accept(@NotNull final DlangVisitor visitor) {
        visitor.visitDestructor(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageFunctionBody getFunctionBody() {
        return PsiTreeUtil.getChildOfType(this, DLanguageFunctionBody.class);
    }

    @Nullable
    public PsiElement getOP_SCOLON() {
        return findChildByType(OP_SCOLON);
    }

    @Nullable
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(OP_PAR_RIGHT);
    }

    @Nullable
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(OP_PAR_LEFT);
    }

    @Nullable
    public PsiElement getKW_THIS() {
        return findChildByType(KW_THIS);
    }

    @Nullable
    public PsiElement getOP_TILDA() {
        return findChildByType(OP_TILDA);
    }

    @NotNull
    public List<DLanguageMemberFunctionAttribute> getMemberFunctionAttributes() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageMemberFunctionAttribute.class);
    }
}
