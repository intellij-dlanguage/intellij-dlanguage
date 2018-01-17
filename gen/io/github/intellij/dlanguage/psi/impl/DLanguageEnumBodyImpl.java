package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACES_LEFT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACES_RIGHT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_COMMA;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_SCOLON;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageEnumBody;
import io.github.intellij.dlanguage.psi.named.DlangEnumMember;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageEnumBodyImpl extends ASTWrapperPsiElement implements DLanguageEnumBody {

    public DLanguageEnumBodyImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitEnumBody(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
    }

    @Nullable
    public PsiElement getOP_BRACES_RIGHT() {
        return findChildByType(OP_BRACES_RIGHT);
    }

    @Nullable
    public PsiElement getOP_BRACES_LEFT() {
        return findChildByType(OP_BRACES_LEFT);
    }

    @NotNull
    public List<DlangEnumMember> getEnumMembers() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DlangEnumMember.class);
    }

    @NotNull
    public List<PsiElement> getOP_COMMAs() {
        return findChildrenByType(OP_COMMA);
    }

    @Nullable
    public PsiElement getOP_SCOLON() {
        return findChildByType(OP_SCOLON);
    }

}
