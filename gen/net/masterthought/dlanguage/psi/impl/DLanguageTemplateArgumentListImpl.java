package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageTemplateArgument;
import net.masterthought.dlanguage.psi.DLanguageTemplateArgumentList;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.masterthought.dlanguage.psi.DLanguageTypes.OP_COMMA;


public class DLanguageTemplateArgumentListImpl extends ASTWrapperPsiElement implements DLanguageTemplateArgumentList {
    public DLanguageTemplateArgumentListImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitTemplateArgumentList(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @NotNull
    public List<PsiElement> getOP_COMMAs() {
        return findChildrenByType(OP_COMMA);
    }

    @NotNull
    public List<DLanguageTemplateArgument> getTemplateArguments() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageTemplateArgument.class);
    }
}
