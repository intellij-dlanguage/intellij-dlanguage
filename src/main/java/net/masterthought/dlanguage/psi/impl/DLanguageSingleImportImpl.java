package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import net.masterthought.dlanguage.psi.DLanguageIdentifier;
import net.masterthought.dlanguage.psi.DLanguageIdentifierChain;
import net.masterthought.dlanguage.psi.DLanguageSingleImport;
import net.masterthought.dlanguage.stubs.DLanguageSingleImportStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DLanguageTypes.*;

/**
 * Created by francis on 7/14/2017.
 */
public class DLanguageSingleImportImpl extends DNamedStubbedPsiElementBase<DLanguageSingleImportStub> implements DLanguageSingleImport {

    public DLanguageSingleImportImpl(@NotNull DLanguageSingleImportStub stub, IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DLanguageSingleImportImpl(ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public DLanguageIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this,DLanguageIdentifier.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_EQ() {
        return findChildByType(OP_EQ);
    }

    @Nullable
    @Override
    public DLanguageIdentifierChain getIdentifierChain() {
        return PsiTreeUtil.getChildOfType(this,DLanguageIdentifierChain.class);
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        getIdentifier().setName(name);//todo check
        return this;
    }
}
