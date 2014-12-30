package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.psi.DNamedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DSymbol extends DNamedElement {

    @Nullable
    PsiReference getReference();

    @NotNull
    String getName();

    @NotNull
    PsiElement setName(String newName);

    @NotNull
    PsiElement getNameIdentifier();

}

