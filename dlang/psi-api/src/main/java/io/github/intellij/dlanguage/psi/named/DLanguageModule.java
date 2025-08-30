package io.github.intellij.dlanguage.psi.named;

import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.psi.PsiCheckedRenameElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiQualifiedNamedElement;
import com.intellij.psi.search.GlobalSearchScope;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a Dlang package.
 */
public interface DLanguageModule extends DNamedElement, PsiCheckedRenameElement, NavigationItem,
    PsiQualifiedNamedElement {

    DLanguageModule[] EMPTY_ARRAY = new DLanguageModule[0];

    @Override
    @NotNull
    @NlsSafe
    String getQualifiedName();

    /**
     * Returns the parent of the package.
     *
     * @return the parent package, or null for no package.
     */
    @Nullable
    DLanguagePackage getParentPackage();

    /**
     * Returns the file that represents the module.
     */
    PsiFile getFile(@NotNull GlobalSearchScope searchScope);

    @Override
    @NotNull
    @NlsSafe
    String getName();
}
