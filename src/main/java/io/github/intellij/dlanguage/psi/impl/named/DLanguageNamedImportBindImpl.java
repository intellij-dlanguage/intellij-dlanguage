package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DLanguageNamedImportBind;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.stubs.DlangNamedImportBindStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_EQ;


public class DLanguageNamedImportBindImpl extends DNamedStubbedPsiElementBase<DlangNamedImportBindStub> implements DLanguageNamedImportBind {

    public DLanguageNamedImportBindImpl(final DlangNamedImportBindStub stub, final IStubElementType type) {
        super(stub, type);
    }

    public DLanguageNamedImportBindImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DlangVisitor visitor) {
        visitor.visitNamedImportBind(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
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

    @Nullable
    @Override
    public DlangIdentifier getNameIdentifier() {
        return getIdentifier();
    }

}
