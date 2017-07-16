package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DLanguageStructDeclarationStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageStructDeclaration extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageStructDeclarationStub> {
    @Nullable
    public PsiElement getKW_STRUCT();

    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    public DLanguageConstraint getConstraint();

    @Nullable
    public DLanguageStructBody getStructBody();

    @Nullable
    public PsiElement getOP_SCOLON();

}
