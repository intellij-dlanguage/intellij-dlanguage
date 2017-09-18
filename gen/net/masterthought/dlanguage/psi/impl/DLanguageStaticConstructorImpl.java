package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageFunctionBody;
import net.masterthought.dlanguage.psi.DLanguageStaticConstructor;
import net.masterthought.dlanguage.psi.DlangVisitor;
import net.masterthought.dlanguage.stubs.DlangStaticConstructorStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.*;


public class DLanguageStaticConstructorImpl extends DStubbedPsiElementBase<DlangStaticConstructorStub> implements DLanguageStaticConstructor {
    public DLanguageStaticConstructorImpl(ASTNode node) {
        super(node);
    }

    public DLanguageStaticConstructorImpl(DlangStaticConstructorStub stub, IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitStaticConstructor(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public PsiElement getKW_STATIC() {
        return findChildByType(KW_STATIC);
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
