package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageDestructor;
import net.masterthought.dlanguage.psi.DLanguageFunctionBody;
import net.masterthought.dlanguage.psi.DLanguageMemberFunctionAttribute;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import net.masterthought.dlanguage.stubs.DLanguageAliasInitializerStub;
import net.masterthought.dlanguage.stubs.DLanguageDestructorStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.masterthought.dlanguage.psi.DLanguageTypes.*;


public class DLanguageDestructorImpl extends DStubbedPsiElementBase<DLanguageDestructorStub> implements DLanguageDestructor {
    public DLanguageDestructorImpl(DLanguageDestructorStub stub, IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DLanguageDestructorImpl(ASTNode node) {
        super(node);

    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitDestructor(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
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
