package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.stubs.DLanguageFunctionDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by francis on 7/14/2017.
 */
public class DLanguageFunctionDeclarationImpl extends DNamedStubbedPsiElementBase<DLanguageFunctionDeclarationStub> implements DLanguageFunctionDeclaration {

    public DLanguageFunctionDeclarationImpl(@NotNull DLanguageFunctionDeclarationStub stub, IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DLanguageFunctionDeclarationImpl(ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this,DLanguageType.class);
    }

    @Nullable
    @Override
    public DLanguageIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this,DLanguageIdentifier.class);
    }

    @Nullable
    @Override
    public DLanguageTemplateParameters getTemplateParameters() {
        return PsiTreeUtil.getChildOfType(this,DLanguageTemplateParameters.class);
    }

    @Nullable
    @Override
    public DLanguageParameters getParameters() {
        return PsiTreeUtil.getChildOfType(this,DLanguageParameters.class);
    }

    @Nullable
    @Override
    public DLanguageConstraint getConstraint() {
        return PsiTreeUtil.getChildOfType(this,DLanguageConstraint.class);
    }

    @Nullable
    @Override
    public DLanguageFunctionBody getFunctionBody() {
        return PsiTreeUtil.getChildOfType(this,DLanguageFunctionBody.class);
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        return getIdentifier().setName(name);
    }
}
