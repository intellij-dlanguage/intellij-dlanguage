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
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Optional;

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
    public String getModuleName() {
        final DLanguageModuleDeclaration module = findChildByClass(DLanguageModuleDeclaration.class);

        if (module == null) {
            return null;
        }

        String[] segments = module.getText()
                            .substring(6, module.getTextLength() - 1)
                            .split("\\.");

        return Optional.of(segments)
                       .filter(ArrayUtils::isEmpty)
                       .map(sc -> sc[segments.length - 1])
                       .orElse(null);
    }

    /**
     * Returns the module name without parent packages, as defined in the file.
     *
     * If no module declaration is present, the default D module name is used
     * (file name without extension).
     */
    @NotNull
    public String getUnqualifiedModuleName() {
        final String fullModuleName = getModuleName();
        final String moduleName;
        if (fullModuleName != null) {
            moduleName = fullModuleName.substring(fullModuleName.lastIndexOf('.') + 1);
        } else {
            moduleName = getVirtualFile().getNameWithoutExtension();
        }
        return moduleName;
    }

    /**
     * Returns the module name if it exists, otherwise returns the file name.
     */
    @NotNull
    public String getModuleOrFileName() {
        final String moduleName = getModuleName();
        return moduleName == null ? super.getName() : moduleName;
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

    @NotNull
    @Override
    public String getName() {
        return getModuleOrFileName();
    }

    @Override
    public PsiElement setName(@NotNull final String name) throws IncorrectOperationException {
        final DLanguageModuleDeclaration module = findChildByClass(
            DLanguageModuleDeclaration.class);
        final String nameWithoutDotD;
        if (name.endsWith(".d")) {
            nameWithoutDotD = name.substring(0, name.length() - 2);
        } else {
            nameWithoutDotD = name;
        }
        if (module != null) {
            module.setName(nameWithoutDotD);
        }
        return super.setName(nameWithoutDotD + ".d");

    }
}
