package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_COMMA;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageForeachTypeList;
import io.github.intellij.dlanguage.psi.named.DlangForeachType;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public class DLanguageForeachTypeListImpl extends ASTWrapperPsiElement implements
    DLanguageForeachTypeList {

    public DLanguageForeachTypeListImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitForeachTypeList(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @NotNull
    public List<DlangForeachType> getForeachTypes() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DlangForeachType.class);
    }

    @NotNull
    public List<PsiElement> getOP_COMMAs() {
        return findChildrenByType(OP_COMMA);
    }

}
