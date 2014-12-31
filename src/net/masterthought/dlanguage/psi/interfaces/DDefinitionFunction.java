package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.DNamedElement;
import net.masterthought.dlanguage.stubs.DDefinitionFunctionStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DDefinitionFunction extends DNamedElement, StubBasedPsiElement<DDefinitionFunctionStub> {

    @NotNull
    PsiElement getSymbol();

    @NotNull
    String getName();

    @Nullable
    PsiElement getNameIdentifier();

//    @NotNull
//    PsiReference getReference();

    @Nullable
    PsiElement setName(String newName);

    @NotNull
    ItemPresentation getPresentation();

}

