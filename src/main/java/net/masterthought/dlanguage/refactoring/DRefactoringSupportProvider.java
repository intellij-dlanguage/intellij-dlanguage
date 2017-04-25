package net.masterthought.dlanguage.refactoring;

import com.intellij.lang.refactoring.RefactoringSupportProvider;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.DLanguageIdentifier;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis on 4/18/2017.
 */
public class DRefactoringSupportProvider extends RefactoringSupportProvider {
    @Override
    public boolean isSafeDeleteAvailable(@NotNull PsiElement element) {
        final PsiElement resolve = element.getReference().resolve();
        return resolve != null && element instanceof DLanguageIdentifier;
    }
}
