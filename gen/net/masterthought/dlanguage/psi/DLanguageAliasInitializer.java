package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DLanguageAliasInitializerStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageAliasInitializer extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageAliasInitializerStub> {
    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_EQ();

    @NotNull
    List<DLanguageStorageClass> getStorageClasss();

    @Nullable
    DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    DLanguageType getType();
}
