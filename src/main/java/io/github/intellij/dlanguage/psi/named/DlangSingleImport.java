package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageIdentifierChain;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.DlangSingleImportStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;


public interface DlangSingleImport extends PsiElement, DNamedElement, StubBasedPsiElement<DlangSingleImportStub> {
    @Nullable
    PsiElement getIdentifier();

    @Nullable
    PsiElement getOP_EQ();

    @Nullable
    DLanguageIdentifierChain getIdentifierChain();

    boolean hasImportBinds();

    @NotNull
    Set<String> getApplicableImportBinds();

    @NotNull
    Map<String, String> getApplicableNamedImportBinds();

    @NotNull
    String getImportedModuleName();
}
