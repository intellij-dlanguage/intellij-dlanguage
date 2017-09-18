package net.masterthought.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DlangIdentifier;
import net.masterthought.dlanguage.psi.DLanguageNamedImportBind;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import net.masterthought.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import net.masterthought.dlanguage.stubs.DlangNamedImportBindStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_EQ;


public class DLanguageNamedImportBindImpl extends DNamedStubbedPsiElementBase<DlangNamedImportBindStub> implements DLanguageNamedImportBind {

    public DLanguageNamedImportBindImpl(final DlangNamedImportBindStub stub, final IStubElementType type) {
        super(stub, type);
    }

    public DLanguageNamedImportBindImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DLanguageVisitor visitor) {
        visitor.visitNamedImportBind(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
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
