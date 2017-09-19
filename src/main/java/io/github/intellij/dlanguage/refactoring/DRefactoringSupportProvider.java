package io.github.intellij.dlanguage.refactoring;

import com.intellij.lang.refactoring.RefactoringSupportProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import io.github.intellij.dlanguage.psi.DlangIdentifier;
import io.github.intellij.dlanguage.resolve.DResolveUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Created by francis on 4/18/2017.
 */
public class DRefactoringSupportProvider extends RefactoringSupportProvider {
    @Override
    public boolean isSafeDeleteAvailable(@NotNull final PsiElement element) {
        if (!(element instanceof DlangIdentifier))
            return false;
        final Set<PsiNamedElement> resolve = DResolveUtil.Companion.getInstance(element.getProject()).findDefinitionNode((PsiNamedElement) element, false);
        return resolve.size() == 1;
    }


}
