package net.masterthought.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import net.masterthought.dlanguage.stubs.DLanguageForeachTypeStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DLanguageForeachTypeImpl extends DNamedStubbedPsiElementBase<DLanguageForeachTypeStub> implements DLanguageForeachType {

    public DLanguageForeachTypeImpl(final DLanguageForeachTypeStub stub, final IStubElementType type) {
        super(stub, type);
    }

    public DLanguageForeachTypeImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DLanguageVisitor visitor) {
        visitor.visitForeachType(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getStubChildOfType(this, DlangIdentifier.class);
    }

    @Nullable
    @Override
    public DLanguageTypeConstructors getTypeConstructors() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTypeConstructors.class);
    }

    @Override
    @Nullable
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @Nullable
    public DlangIdentifier getNameIdentifier() {
        return getIdentifier();
    }

}
