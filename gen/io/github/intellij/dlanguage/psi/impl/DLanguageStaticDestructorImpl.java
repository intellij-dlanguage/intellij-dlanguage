package io.github.intellij.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageFunctionBody;
import io.github.intellij.dlanguage.psi.DLanguageStaticDestructor;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.stubs.DlangStaticDestructorStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageStaticDestructorImpl extends
    DStubbedPsiElementBase<DlangStaticDestructorStub> implements DLanguageStaticDestructor {

    public DLanguageStaticDestructorImpl(ASTNode node) {
        super(node);
    }

    public DLanguageStaticDestructorImpl(DlangStaticDestructorStub stub,
        IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitStaticDestructor(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getOP_TILDA() {
        return findChildByType(DlangTypes.OP_TILDA);
    }

    @Nullable
    public PsiElement getKW_STATIC() {
        return findChildByType(DlangTypes.KW_STATIC);
    }

    @Nullable
    public PsiElement getKW_THIS() {
        return findChildByType(DlangTypes.KW_THIS);
    }

    @Nullable
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(DlangTypes.OP_PAR_LEFT);
    }

    @Nullable
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(DlangTypes.OP_PAR_RIGHT);
    }

    @Nullable
    public DLanguageFunctionBody getFunctionBody() {
        return PsiTreeUtil.getChildOfType(this, DLanguageFunctionBody.class);
    }
}
