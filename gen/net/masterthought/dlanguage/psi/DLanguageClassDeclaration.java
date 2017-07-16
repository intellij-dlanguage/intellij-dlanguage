package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageClassDeclaration extends PsiElement {
    @Nullable
    public PsiElement getKW_CLASS();

    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_SCOLON();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public DLanguageStructBody getStructBody();

    @Nullable
    public DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    public DLanguageConstraint getConstraint();

    @Nullable
    public DLanguageBaseClassList getBaseClassList();
}
