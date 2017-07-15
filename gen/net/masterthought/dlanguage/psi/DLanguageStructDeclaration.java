package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageStructDeclaration extends PsiElement, DNamedElement {
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
