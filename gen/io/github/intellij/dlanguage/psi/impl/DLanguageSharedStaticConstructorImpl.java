package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_SHARED;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_STATIC;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_THIS;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PAR_LEFT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PAR_RIGHT;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageFunctionBody;
import io.github.intellij.dlanguage.psi.DLanguageSharedStaticConstructor;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.stubs.DlangSharedStaticConstructorStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageSharedStaticConstructorImpl extends
    DStubbedPsiElementBase<DlangSharedStaticConstructorStub> implements
    DLanguageSharedStaticConstructor {

    public DLanguageSharedStaticConstructorImpl(ASTNode node) {
        super(node);
    }

    public DLanguageSharedStaticConstructorImpl(DlangSharedStaticConstructorStub stub,
        IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitSharedStaticConstructor(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
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
