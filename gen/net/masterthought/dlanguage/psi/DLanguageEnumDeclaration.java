package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DLanguageEnumDeclarationStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageEnumDeclaration extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageEnumDeclarationStub> {
    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public PsiElement getKW_ENUM();

    @Nullable
    public DLanguageType getType();

    @Nullable
    public DLanguageEnumBody getEnumBody();
}
