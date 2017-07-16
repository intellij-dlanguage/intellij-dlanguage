package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import net.masterthought.dlanguage.psi.DLanguageExpression;
import net.masterthought.dlanguage.psi.DLanguageIdentifier;
import net.masterthought.dlanguage.psi.DLanguageIfCondition;
import net.masterthought.dlanguage.psi.DLanguageType;
import net.masterthought.dlanguage.stubs.DLanguageIdentifierStub;
import net.masterthought.dlanguage.stubs.DLanguageIfConditionStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DLanguageTypes.KW_AUTO;
import static net.masterthought.dlanguage.psi.DLanguageTypes.OP_EQ;

/**
 * Created by francis on 7/14/2017.
 */
public class DLanguageIfConditionImpl extends DNamedStubbedPsiElementBase<DLanguageIfConditionStub> implements DLanguageIfCondition {


    public DLanguageIfConditionImpl(@NotNull DLanguageIfConditionStub stub, IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DLanguageIfConditionImpl(ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public DLanguageIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this,DLanguageIdentifier.class);
    }

    @Nullable
    @Override
    public DLanguageExpression getExpression() {
        return PsiTreeUtil.getChildOfType(this,DLanguageExpression.class);
    }

    @Nullable
    @Override
    public PsiElement getKW_AUTO() {
        return findChildByType(KW_AUTO);
    }

    @Nullable
    @Override
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this,DLanguageType.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_EQ() {
        return findChildByType(OP_EQ);
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        return getIdentifier().setName(name);
    }
}
