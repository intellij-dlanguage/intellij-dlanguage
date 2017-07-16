package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageBlockStatement;
import net.masterthought.dlanguage.psi.DLanguageUnittest;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import net.masterthought.dlanguage.stubs.interfaces.DLanguageUnittestStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageUnittestImpl extends DStubbedPsiElementBase<DLanguageUnittestStub> implements DLanguageUnittest {
    public DLanguageUnittestImpl(ASTNode node) {
        super(node);
    }

    public DLanguageUnittestImpl(DLanguageUnittestStub stub, IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitUnittest(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
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
