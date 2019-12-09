package io.github.intellij.dlanguage.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.IncorrectOperationException;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.DlangFileType;
import io.github.intellij.dlanguage.psi.named.DLanguageModuleDeclaration;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImplUtil;
import io.github.intellij.dlanguage.stubs.DlangFileStub;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.removeEnd;

public class DlangFile extends PsiFileBase {

    public DlangFile(@NotNull final FileViewProvider viewProvider) {
        super(viewProvider, DLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return DlangFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "D Language File";
    }

    @Override
    public Icon getIcon(final int flags) {
        return super.getIcon(flags);
    }

    /**
     * Returns the module name defined in the file or null if it doesn't exist.
     */
    @Nullable
    public String getFullyQualifiedModuleName() {
        return Optional.ofNullable(findChildByClass(DLanguageModuleDeclaration.class))
                       .map(DLanguageModuleDeclaration::getIdentifierChain)
                       .map(DLanguageIdentifierChain::getIdentifiers)
                       .map(identifiers -> identifiers.get(identifiers.size() - 1))
                       .map(PsiElement::getText)
                       .orElse(null);
    }

    /**
     * Returns the module name without parent packages, as defined in the file.
     *
     * If no module declaration is present, the default D module name is used
     * (file name without extension).
     */
    @NotNull
    public String getModuleName() {
        return Optional.ofNullable(findChildByClass(DLanguageModuleDeclaration.class))
                       .map(DLanguageModuleDeclaration::getIdentifierChain)
                       .map(DLanguageIdentifierChain::getIdentifiers)
                       .filter(list -> !list.isEmpty())
                       .map(identifiers -> identifiers.get(identifiers.size() - 1))
                       .map(PsiElement::getText)
                       .orElseGet(() -> StringUtils.removeEnd(this.getName(), ".d"));
    }

    /**
     * Returns the module name if it exists, otherwise returns the file name.
     */
    @NotNull
    public String getModuleOrFileName() {
        return Optional.ofNullable(this.getFullyQualifiedModuleName())
                       .orElseGet(this::getName);
    }

    /**
     * Generates a stub for the current file, particularly so we can index names.
     */
    @Nullable
    @Override
    public DlangFileStub getStub() {
        return (DlangFileStub) super.getStub();
    }

    @Override
    public boolean processDeclarations(@NotNull final PsiScopeProcessor processor, @NotNull final ResolveState state, final PsiElement lastParent, @NotNull final PsiElement place) {
        boolean toContinue = true;
        for (final PsiElement element : getChildren()) {
            if (element instanceof DLanguageDeclaration) {
                if (!ScopeProcessorImplUtil.INSTANCE.processDeclaration((DLanguageDeclaration) element, processor, state, lastParent, place)) {
                    toContinue = false;
                }
            }
            if (element instanceof DLanguageModuleDeclaration) {
                if (!processor.execute(element, state)) {
                    toContinue = false;
                }
            }
        }
        return toContinue;
    }

    @Override
    public PsiElement setName(@NotNull final String name) throws IncorrectOperationException {
        final DLanguageModuleDeclaration module = findChildByClass(DLanguageModuleDeclaration.class);
        final String extensionLessName = removeEnd(name, ".d");

        if (module != null) {
            module.setName(extensionLessName);
        }
        return super.setName(extensionLessName + ".d");

    }
}
