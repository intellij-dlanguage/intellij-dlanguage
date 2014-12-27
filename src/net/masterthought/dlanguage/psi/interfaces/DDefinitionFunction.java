package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.DNamedElement;
import net.masterthought.dlanguage.stubs.DDefinitionFunctionStub;
import org.jetbrains.annotations.NotNull;

public interface DDefinitionFunction extends DNamedElement, StubBasedPsiElement<DDefinitionFunctionStub> {

    @NotNull
    PsiElement getSymbol();

}

