package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.DLanguageTypeConstructors;
import io.github.intellij.dlanguage.psi.named.DlangForeachType;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.stubs.DlangForeachTypeStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DLanguageForeachTypeImpl extends
    DNamedStubbedPsiElementBase<DlangForeachTypeStub> implements DlangForeachType {

    public DLanguageForeachTypeImpl(final DlangForeachTypeStub stub, final IStubElementType type) {
        super(stub, type);
    }

    public DLanguageForeachTypeImpl(final ASTNode node) {
        super(node);
    }

    public void accept(@NotNull final DlangVisitor visitor) {
        visitor.visitDNamedElement(this);
        visitor.visitForeachType(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
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
