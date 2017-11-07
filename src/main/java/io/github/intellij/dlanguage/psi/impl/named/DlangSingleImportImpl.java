package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributesFinder;
import io.github.intellij.dlanguage.stubs.DlangSingleImportStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by francis on 7/14/2017.
 * todo remove al references to a single imports name. This no longer means the same thing
 */
public class DlangSingleImportImpl extends DNamedStubbedPsiElementBase<DlangSingleImportStub> implements DlangSingleImport {

    public DlangSingleImportImpl(@NotNull final DlangSingleImportStub stub, final IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DlangSingleImportImpl(final ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_EQ() {
        return findChildByType(DlangTypes.OP_EQ);
    }

    @Nullable
    @Override
    public DLanguageIdentifierChain getIdentifierChain() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIdentifierChain.class);
    }

    @NotNull
    @Override
    public Set<String> getApplicableImportBinds() {
        if (getStub() != null) {
            assert getStub() != null;
            try {
                return getStub().getApplicableImportBinds();
            } catch (final NullPointerException e) {
                e.printStackTrace();
            }
        }
        if (((DLanguageImportDeclaration) getParent()).getImportBindings() != null) {
            return ((DLanguageImportDeclaration) getParent()).getImportBindings().getImportBinds().stream().map(dLanguageImportBind -> dLanguageImportBind.getIdentifier().getName()).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    @NotNull
    @Override
    public String getImportedModuleName() {
        if (getGreenStub() != null) {
            return getGreenStub().getImportedModule();
        }
        if (getIdentifierChain().getText().equals(""))
            throw new IllegalStateException();
        return getIdentifierChain().getText();
    }

    @Nullable
    @Override
    public DlangIdentifier getNameIdentifier() {
        return getIdentifier();
    }

    public Boolean isPublic() {
        if (getGreenStub() != null)
            return getGreenStub().isPublic();
        if (getIdentifierChain() == null || getIdentifierChain().getIdentifiers().isEmpty())
            return false;
        final DAttributesFinder finder = new DAttributesFinder(getIdentifierChain().getIdentifiers().get(0));
        finder.recurseUp();
        return finder.isPublic();
    }
}
