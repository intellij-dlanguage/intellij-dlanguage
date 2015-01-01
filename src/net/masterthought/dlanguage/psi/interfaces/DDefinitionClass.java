package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.DNamedElement;
import net.masterthought.dlanguage.stubs.DDefinitionClassStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DDefinitionClass extends DNamedElement, StubBasedPsiElement<DDefinitionClassStub> {

    @NotNull
    PsiElement getSymbol();

    @NotNull
    String getName();

    @Nullable
    PsiElement getNameIdentifier();

    @Nullable
    PsiElement setName(String newName);

    @NotNull
    ItemPresentation getPresentation();
}

