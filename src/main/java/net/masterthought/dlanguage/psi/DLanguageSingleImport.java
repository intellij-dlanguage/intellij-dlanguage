package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DLanguageSingleImportStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;


public interface DLanguageSingleImport extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageSingleImportStub> {
    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_EQ();

    @Nullable
    DLanguageIdentifierChain getIdentifierChain();

    @NotNull
    Set<String> getApplicableImportBinds();

    @NotNull
    String getImportedModuleName();
}
