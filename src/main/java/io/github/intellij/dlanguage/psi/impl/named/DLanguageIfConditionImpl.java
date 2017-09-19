package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageExpression;
import io.github.intellij.dlanguage.psi.DLanguageIfCondition;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.stubs.DlangIfConditionStub;
import io.github.intellij.dlanguage.psi.DLanguageExpression;
import io.github.intellij.dlanguage.psi.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DLanguageIfCondition;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.stubs.DlangIfConditionStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_AUTO;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_EQ;

/**
 * Created by francis on 7/14/2017.
 */
public class DLanguageIfConditionImpl extends DNamedStubbedPsiElementBase<DlangIfConditionStub> implements DLanguageIfCondition {
    public DLanguageIfConditionImpl(@NotNull final DlangIfConditionStub stub, final IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DLanguageIfConditionImpl(final ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public DlangIdentifier getNameIdentifier() {
        return getIdentifier();
    }

    @Nullable
    @Override
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
    }

    @Nullable
    @Override
    public DLanguageExpression getExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageExpression.class);
    }

    @Nullable
    @Override
    public PsiElement getKW_AUTO() {
        return findChildByType(DlangTypes.KW_AUTO);
    }

    @Nullable
    @Override
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_EQ() {
        return findChildByType(DlangTypes.OP_EQ);
    }
}
