package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageTemplateParameter;
import net.masterthought.dlanguage.psi.DLanguageTemplateParameterList;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_COMMA;


public class DLanguageTemplateParameterListImpl extends ASTWrapperPsiElement implements DLanguageTemplateParameterList {
    public DLanguageTemplateParameterListImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitTemplateParameterList(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @NotNull
    public List<DLanguageTemplateParameter> getTemplateParameters() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageTemplateParameter.class);
    }

    @NotNull
    public List<PsiElement> getOP_COMMAs() {
        return findChildrenByType(OP_COMMA);
    }

}
