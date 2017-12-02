package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACKET_LEFT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACKET_RIGHT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_COMMA;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageArrayInitializer;
import io.github.intellij.dlanguage.psi.DLanguageArrayMemberInitialization;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageArrayInitializerImpl extends ASTWrapperPsiElement implements
    DLanguageArrayInitializer {

    public DLanguageArrayInitializerImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitArrayInitializer(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @NotNull
    public List<PsiElement> getOP_COMMAs() {
        return findChildrenByType(OP_COMMA);
    }

    @NotNull
    public List<DLanguageArrayMemberInitialization> getArrayMemberInitializations() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageArrayMemberInitialization.class);
    }

    @Nullable
    public PsiElement getOP_BRACKET_RIGHT() {
        return findChildByType(OP_BRACKET_RIGHT);
    }

    @Nullable
    public PsiElement getOP_BRACKET_LEFT() {
        return findChildByType(OP_BRACKET_LEFT);
    }

}
