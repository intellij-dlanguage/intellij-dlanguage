package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.DlangSingleImportStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;


public interface DlangSingleImport extends PsiElement, DNamedElement, StubBasedPsiElement<DlangSingleImportStub> {
    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_EQ();

    @Nullable
    DLanguageIdentifierChain getIdentifierChain();

    @NotNull
    Set<String> getApplicableImportBinds();

    @NotNull
    String getImportedModuleName();
}
