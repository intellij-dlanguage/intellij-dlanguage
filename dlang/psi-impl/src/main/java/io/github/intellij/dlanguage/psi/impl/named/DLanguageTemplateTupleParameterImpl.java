package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import io.github.intellij.dlanguage.psi.DLanguageTemplateTupleParameter;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.stubs.DLanguageTemplateTupleParameterStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.psi.DlangTypes.ID;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_TRIPLEDOT;


public class DLanguageTemplateTupleParameterImpl extends
    DNamedStubbedPsiElementBase<DLanguageTemplateTupleParameterStub> implements DLanguageTemplateTupleParameter {

    public DLanguageTemplateTupleParameterImpl(ASTNode node) {
        super(node);
    }

    @Override
    public @Nullable PsiElement getNameIdentifier() {
        return getIdentifier();
    }

    public DLanguageTemplateTupleParameterImpl(DLanguageTemplateTupleParameterStub stub,
        IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitTemplateTupleParameter(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getIdentifier() {
        return findChildByType(ID);
    }

    @Nullable
    public PsiElement getOP_TRIPLEDOT() {
        return findChildByType(OP_TRIPLEDOT);
    }

}
