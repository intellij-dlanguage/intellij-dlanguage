package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.DLanguageTypeConstructor;
import io.github.intellij.dlanguage.psi.named.DlangForeachType;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.stubs.DlangForeachTypeStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;

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

    @NotNull
    @Override
    public List<DLanguageTypeConstructor> getTypeConstructors() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageTypeConstructor.class);
    }

    @Override
    @Nullable
    public PsiElement getKW_REF() {
        return findChildByType(KW_REF);
    }

    @Override
    @Nullable
    public PsiElement getKW_ALIAS() {
        return findChildByType(KW_ALIAS);
    }

    @Override
    @Nullable
    public PsiElement getKW_ENUM() {
        return findChildByType(KW_ENUM);
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
