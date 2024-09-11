package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.DLanguageTypeConstructor;
import io.github.intellij.dlanguage.psi.named.DLanguageForeachType;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.stubs.DLanguageForeachTypeStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;

public class DLanguageForeachTypeImpl extends
    DNamedStubbedPsiElementBase<DLanguageForeachTypeStub> implements DLanguageForeachType {

    public DLanguageForeachTypeImpl(final DLanguageForeachTypeStub stub, final IStubElementType type) {
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
    public PsiElement getIdentifier() {
        return findChildByType(ID);
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
    public PsiElement getNameIdentifier() {
        return getIdentifier();
    }

}
