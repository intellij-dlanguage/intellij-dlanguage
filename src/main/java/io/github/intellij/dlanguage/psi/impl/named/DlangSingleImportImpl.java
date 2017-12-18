package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageIdentifierChain;
import io.github.intellij.dlanguage.psi.DLanguageImportBind;
import io.github.intellij.dlanguage.psi.DLanguageImportBindings;
import io.github.intellij.dlanguage.psi.DLanguageImportDeclaration;
import io.github.intellij.dlanguage.psi.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DlangSingleImport;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.psi.references.DReference;
import io.github.intellij.dlanguage.stubs.DlangSingleImportStub;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    public void accept(@NotNull final DlangVisitor visitor) {visitor.visitDNamedElement(this);
        visitor.visitSingleImport(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
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
        if (getGreenStub() != null) {
            try {
                return getGreenStub().getApplicableImportBinds();
            } catch (final NullPointerException e) {
                e.printStackTrace();
            }
        }
        final DLanguageImportBindings importBindings = ((DLanguageImportDeclaration) getParent())
            .getImportBindings();
        if (importBindings != null) {
            final Set<String> set = new HashSet<>();
            for (final DLanguageImportBind dLanguageImportBind : importBindings.getImportBinds()) {
                if (dLanguageImportBind.getIdentifier() != null) {
                    final String name = dLanguageImportBind.getIdentifier().getName();
                    set.add(name);
                }
            }
            return set;
        }
        return new HashSet<>();
    }

    @NotNull
    @Override
    public String getImportedModuleName() {
        if (getGreenStub() != null) {
            return getGreenStub().getImportedModule();
        }
        if (getIdentifierChain() == null) {
            return DReference.Companion.getNAME_NOT_FOUND_STRING();
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
    public boolean hasAName() {
        try {
            return getIdentifier() != null;
        } catch (final NullPointerException e) {
            return false;
        }
    }
}
