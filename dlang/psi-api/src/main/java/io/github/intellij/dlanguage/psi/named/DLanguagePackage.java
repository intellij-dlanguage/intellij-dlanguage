package io.github.intellij.dlanguage.psi.named;

import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.psi.PsiCheckedRenameElement;
import com.intellij.psi.PsiDirectoryContainer;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiQualifiedNamedElement;
import com.intellij.psi.search.GlobalSearchScope;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a Dlang package.
 */
public interface DLanguagePackage extends DNamedElement, PsiCheckedRenameElement, NavigationItem,
    PsiDirectoryContainer, PsiQualifiedNamedElement {

    DLanguagePackage[] EMPTY_ARRAY = new DLanguagePackage[0];

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
     * Return the list of subpackages of this package under all source roots of the project.
     *
     * @return the array of subpackages.
     */
    DLanguagePackage[] getSubPackages();

    /**
     * Return the list of subpackages of this package in the specified search scope.
     *
     * @param scope the scope in which packages are searches.
     * @return the array of subpackages.
     */
    DLanguagePackage[] getSubPackages(@NotNull GlobalSearchScope scope);

    /**
     *
     */
    PsiFile @NotNull [] getFiles(@NotNull GlobalSearchScope searchScope);

    /**
     * This method must be invoked on the packege after all directories corresponding
     * to it have been renamed/moved accordingly to the qualified name change.
     *
     * @param newQualifiedName the new qualified name of the package.
     */
    void handleQualifiedNameChange(@NotNull String newQualifiedName);

    @Override
    @NotNull
    @NlsSafe
    String getName();
}
