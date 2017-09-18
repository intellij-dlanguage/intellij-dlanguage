package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageFunctionBody;
import net.masterthought.dlanguage.psi.DLanguageSharedStaticDestructor;
import net.masterthought.dlanguage.psi.DlangVisitor;
import net.masterthought.dlanguage.stubs.DlangSharedStaticDestructorStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.*;


public class DLanguageSharedStaticDestructorImpl extends DStubbedPsiElementBase<DlangSharedStaticDestructorStub> implements DLanguageSharedStaticDestructor {
    public DLanguageSharedStaticDestructorImpl(ASTNode node) {
        super(node);
    }

    public DLanguageSharedStaticDestructorImpl(DlangSharedStaticDestructorStub stub, IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitSharedStaticDestructor(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public PsiElement getOP_TILDA() {
        return findChildByType(OP_TILDA);
    }

    @Nullable
    public PsiElement getKW_STATIC() {
        return findChildByType(KW_STATIC);
    }

    @Nullable
    public PsiElement getKW_SHARED() {
        return findChildByType(KW_SHARED);
    }

    @Nullable
    public PsiElement getKW_THIS() {
        return findChildByType(KW_THIS);
    }

    @Nullable
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(OP_PAR_LEFT);
    }

    @Nullable
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(OP_PAR_RIGHT);
    }

    @Nullable
    public DLanguageFunctionBody getFunctionBody() {
        return PsiTreeUtil.getChildOfType(this, DLanguageFunctionBody.class);
    }
}
