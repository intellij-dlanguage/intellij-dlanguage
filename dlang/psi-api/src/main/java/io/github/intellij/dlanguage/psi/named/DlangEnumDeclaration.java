package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageEnumBody;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.Declaration;
import io.github.intellij.dlanguage.psi.interfaces.HasMembers;
import io.github.intellij.dlanguage.stubs.DlangEnumDeclarationStub;
import org.jetbrains.annotations.Nullable;


public interface DlangEnumDeclaration extends PsiElement, DNamedElement, Declaration,
    StubBasedPsiElement<DlangEnumDeclarationStub>, HasMembers<DlangEnumDeclarationStub> {
    @Nullable
    PsiElement getIdentifier();

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    PsiElement getKW_ENUM();

    @Nullable
    DLanguageType getType();

    @Nullable
    DLanguageEnumBody getEnumBody();

//    @NotNull
//    Type getMemtype();
}
