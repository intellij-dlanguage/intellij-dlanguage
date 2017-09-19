package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.psi.DLanguageBaseClass;
import io.github.intellij.dlanguage.psi.DLanguageBaseClassList;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_COMMA;


public class DLanguageBaseClassListImpl extends ASTWrapperPsiElement implements DLanguageBaseClassList {
    public DLanguageBaseClassListImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitBaseClassList(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @NotNull
    public List<DLanguageBaseClass> getBaseClasss() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageBaseClass.class);
    }

    @NotNull
    public List<PsiElement> getOP_COMMAs() {
        return findChildrenByType(DlangTypes.OP_COMMA);
    }

}
