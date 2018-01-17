package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_COMMA;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_EQ;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_SCOLON;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAutoDeclaration;
import io.github.intellij.dlanguage.psi.DLanguageFunctionBody;
import io.github.intellij.dlanguage.psi.DLanguageStorageClass;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.DLanguageVariableDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangDeclarator;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageVariableDeclarationImpl extends ASTWrapperPsiElement implements
    DLanguageVariableDeclaration {

    public DLanguageVariableDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitVariableDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageAutoDeclaration getAutoDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAutoDeclaration.class);
    }

    @Nullable
    public PsiElement getOP_SCOLON() {
        return findChildByType(OP_SCOLON);
    }

    @Nullable
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @NotNull
    public List<DlangDeclarator> getDeclarators() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DlangDeclarator.class);
    }

    @NotNull
    public List<PsiElement> getOP_COMMAs() {
        return findChildrenByType(OP_COMMA);
    }

    @Nullable
    public PsiElement getOP_EQ() {
        return findChildByType(OP_EQ);
    }

    @Nullable
    public DLanguageFunctionBody getFunctionBody() {
        return PsiTreeUtil.getChildOfType(this, DLanguageFunctionBody.class);
    }

    @NotNull
    public List<DLanguageStorageClass> getStorageClasss() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageStorageClass.class);
    }
}
