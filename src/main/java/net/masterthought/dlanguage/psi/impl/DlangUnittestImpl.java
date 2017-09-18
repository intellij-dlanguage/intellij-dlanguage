package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageBlockStatement;
import net.masterthought.dlanguage.psi.DLanguageUnittest;
import net.masterthought.dlanguage.psi.DlangVisitor;
import net.masterthought.dlanguage.stubs.interfaces.DlangUnittestStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DlangUnittestImpl extends DStubbedPsiElementBase<DlangUnittestStub> implements DLanguageUnittest {

    public DlangUnittestImpl(final ASTNode node) {
        super(node);
    }

    public DlangUnittestImpl(final DlangUnittestStub stub, final IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public void accept(@NotNull final DlangVisitor visitor) {
        visitor.visitUnittest(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageUnittest getUnittest() {
        return PsiTreeUtil.getChildOfType(this, DLanguageUnittest.class);
    }

    @Nullable
    public DLanguageBlockStatement getBlockStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageBlockStatement.class);
    }
}
