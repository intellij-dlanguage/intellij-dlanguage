package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageIdentifier extends PsiElement, DNamedElement {
    @Nullable
    public PsiElement getID();

}
