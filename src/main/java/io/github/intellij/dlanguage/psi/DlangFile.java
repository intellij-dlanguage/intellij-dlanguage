package io.github.intellij.dlanguage.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.IncorrectOperationException;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.DlangFileType;
import io.github.intellij.dlanguage.psi.named.DLanguageModuleDeclaration;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImplUtil;
import io.github.intellij.dlanguage.stubs.DlangFileStub;
import javax.swing.Icon;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.removeEnd;

// todo: consider making this class abstract so that there can be x2 classes that inherit from it.
// One for .di files (interfaces) and another for regular .d source files
public class DlangFile extends PsiFileBase implements DlangPsiFile {

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

    private Optional<String> findModuleDeclaration() {
        return Optional.ofNullable(findChildByClass(DLanguageModuleDeclaration.class))
            .map(DLanguageModuleDeclaration::getIdentifierChain)
            .map(DLanguageIdentifierChain::getIdentifiers)
            .filter(CollectionUtils::isNotEmpty)
            .map(identifiers -> identifiers.stream()
                .map(PsiElement::getText)
                .collect(Collectors.joining("."))
            );
    }

    /**
     * A fully qualified module name is the package (if defined) followed by the module name.
     * Both should be lowercase ASCII characters (letters, digits, underscore). Packages correspond
     * to directory names in the source file path. Package and module names cannot be Keywords.
     *
     * An example of such would be "c.stdio", where the stdio module is in the c package.
     *
     * If the module name is not defined within the file then the filename (without extension) is used as the module name.
     */
    @NotNull
    @Override
    public String getFullyQualifiedModuleName() {
        final Optional<String> moduleDeclaration = findModuleDeclaration();

        return moduleDeclaration.orElseGet(this::getModuleName);
    }

    /**
     * D module names are, by default, the file name with the path and extension stripped off.
     * They can be set explicitly with the module declaration.
     * This method attempts to find an explicitly defined module declaration first and then return the filename (without extension)
     * if no module declaration is found.
     */
    @NotNull
    @Override
    public String getModuleName() {
        final Optional<String> moduleDeclaration = findModuleDeclaration();

        return moduleDeclaration
            .filter(StringUtil::isNotEmpty)
            .map(fullDeclaration -> {
                final String[] identifiers = fullDeclaration.split("\\.");
                return identifiers[identifiers.length - 1];
            })
            .orElseGet(() -> StringUtil.trimExtensions(super.getName()));
    }

    @Nullable
    @Override
    public String getPackageName() {
        return StringUtils.trimToNull(StringUtil.trimEnd(
            StringUtil.trimEnd(getFullyQualifiedModuleName(), this.getModuleName()),
            "."
        ));
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

    /**
     * @return the filename including the extension
     */
    @NotNull
    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * Called when a D source file is renamed
     * @param name filename of the source file being renamed
     * @return the new element after the file has been replaced
     * @throws IncorrectOperationException if the user attempts to rename the file in an illegal way. Eg:
     * D file names should be composed of the ASCII characters lower case letters, digits or _ and should also not be a Keyword.
     */
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
