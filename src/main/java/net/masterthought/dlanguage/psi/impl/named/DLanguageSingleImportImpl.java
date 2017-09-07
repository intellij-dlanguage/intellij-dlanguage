package net.masterthought.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageIdentifier;
import net.masterthought.dlanguage.psi.DLanguageIdentifierChain;
import net.masterthought.dlanguage.psi.DLanguageImportDeclaration;
import net.masterthought.dlanguage.psi.DLanguageSingleImport;
import net.masterthought.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import net.masterthought.dlanguage.resolve.processors.parameters.DAttributesFinder;
import net.masterthought.dlanguage.stubs.DLanguageSingleImportStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static net.masterthought.dlanguage.psi.DLanguageTypes.OP_EQ;

/**
 * Created by francis on 7/14/2017.
 * todo remove al references to a single imports name. This no longer means the same thing
 */
public class DLanguageSingleImportImpl extends DNamedStubbedPsiElementBase<DLanguageSingleImportStub> implements DLanguageSingleImport {

    public DLanguageSingleImportImpl(@NotNull final DLanguageSingleImportStub stub, final IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public DLanguageSingleImportImpl(final ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public DLanguageIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
    }

    @Nullable
    @Override
    public PsiElement getOP_EQ() {
        return findChildByType(OP_EQ);
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
        if (getStub() != null) {
            assert getStub() != null;
            try {
                return getStub().getImportedModule();
            } catch (final NullPointerException e) {
                e.printStackTrace();
            }
        }
        return getIdentifierChain().getText();
    }

    @Nullable
    @Override
    public DLanguageIdentifier getNameIdentifier() {
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
