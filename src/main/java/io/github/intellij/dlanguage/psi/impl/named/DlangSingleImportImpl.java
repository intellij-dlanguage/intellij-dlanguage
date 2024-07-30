package io.github.intellij.dlanguage.psi.impl.named;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageIdentifierChain;
import io.github.intellij.dlanguage.psi.DLanguageImportBind;
import io.github.intellij.dlanguage.psi.DLanguageImportBindings;
import io.github.intellij.dlanguage.psi.DLanguageImportDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangSingleImport;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.psi.impl.DNamedStubbedPsiElementBase;
import io.github.intellij.dlanguage.psi.references.DReference;
import io.github.intellij.dlanguage.stubs.DlangSingleImportStub;

import java.util.*;

import io.github.intellij.dlanguage.utils.DPsiUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.utils.DPsiUtilKt.getImportText;

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
    public PsiElement getIdentifier() {
        return findChildByType(DlangTypes.ID);
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

    @Override
    public boolean hasImportBinds() {
        return !getApplicableImportBinds().isEmpty() || !getApplicableNamedImportBinds().isEmpty();
    }

    @NotNull
    @Override
    public Set<String> getApplicableImportBinds() {
        DlangSingleImportStub greenStub = getGreenStub();
        if (greenStub != null) {
            try {
                return greenStub.getApplicableImportBinds();
            } catch (final NullPointerException e) {
                Logger.getInstance(DlangSingleImportImpl.class).error(e);
            }
        }
        final DLanguageImportBindings importBindings = ((DLanguageImportDeclaration) getParent())
            .getImportBindings();
        if (importBindings != null) {
            final Set<String> set = new HashSet<>();
            for (final DLanguageImportBind importBind : importBindings.getImportBinds()) {
                if (importBind.getIdentifier() != null && importBind.getNamedImportBind() == null) {
                    final @NotNull String name = importBind.getIdentifier().getText();
                    set.add(name);
                }
            }
            return set;
        }
        return Collections.emptySet();
    }

    @NotNull
    @Override
    public Map<String, String> getApplicableNamedImportBinds() {
        DlangSingleImportStub greenStub = getGreenStub();
        if (greenStub != null) {
            try {
                return greenStub.getApplicableNamedImportBinds();
            } catch (final NullPointerException e) {
                Logger.getInstance(DlangSingleImportImpl.class).error(e);
            }
        }
        final DLanguageImportBindings importBindings = ((DLanguageImportDeclaration) getParent())
            .getImportBindings();
        if (importBindings != null) {
            final Map<String, String> map = new HashMap<>();
            for (final DLanguageImportBind importBind : importBindings.getImportBinds()) {
                if (importBind.getIdentifier() != null && importBind.getNamedImportBind() != null && importBind.getNamedImportBind().getIdentifier() != null) {
                    final @NotNull String name = importBind.getNamedImportBind().getIdentifier().getText();
                    final @NotNull String bind = importBind.getIdentifier().getText();
                    map.put(name, bind);
                }
            }
            return map;
        }
        return Collections.emptyMap();
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
        if (getIdentifierChain().getIdentifier() == null) {
            Logger.getInstance(getClass())
                .warn("getIdentifier chain was: \"\". Complete text of symbol: " + getText());
            return DReference.Companion.getNAME_NOT_FOUND_STRING();
        }

        return getImportText(getIdentifierChain());
    }

    @Nullable
    @Override
    public PsiElement getNameIdentifier() {
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
