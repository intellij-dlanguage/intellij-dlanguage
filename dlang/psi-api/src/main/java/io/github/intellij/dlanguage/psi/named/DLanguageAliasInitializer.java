
package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageStorageClass;
import io.github.intellij.dlanguage.psi.DLanguageTemplateParameters;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.DTypedElement;
import io.github.intellij.dlanguage.psi.interfaces.Declaration;
import io.github.intellij.dlanguage.psi.types.DType;
import io.github.intellij.dlanguage.stubs.DLanguageAliasInitializerStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageAliasInitializer extends PsiElement, DNamedElement, Declaration, DTypedElement,
    StubBasedPsiElement<DLanguageAliasInitializerStub> {

    @Nullable
    PsiElement getIdentifier();

    @Nullable
    PsiElement getOP_EQ();

    @NotNull
    List<DLanguageStorageClass> getStorageClasss();

    @Nullable
    DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    DLanguageType getType();

    @NotNull
    DType getDType();
}
