
package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageStorageClass;
import io.github.intellij.dlanguage.psi.DLanguageTemplateParameters;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.Declaration;
import io.github.intellij.dlanguage.stubs.DLanguageAliasInitializerStub;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAliasInitializer extends PsiElement, DNamedElement, Declaration,
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
}
