package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DLanguageCatchStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageCatch extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageCatchStub> {
    @Nullable
    public PsiElement getKW_CATCH();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public DLanguageType getType();

    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public DLanguageDeclarationOrStatement getDeclarationOrStatement();
}
