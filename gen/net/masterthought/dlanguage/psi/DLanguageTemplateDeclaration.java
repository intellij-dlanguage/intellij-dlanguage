package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageTemplateDeclaration extends PsiElement, DNamedElement {
    @Nullable
    public PsiElement getKW_TEMPLATE();

    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    public DLanguageConstraint getConstraint();

    @Nullable
    public PsiElement getOP_BRACES_RIGHT();

    @Nullable
    public PsiElement getOP_BRACES_LEFT();

    @NotNull
    public List<DLanguageDeclaration> getDeclarations();

    @Nullable
    public DLanguageEponymousTemplateDeclaration getEponymousTemplateDeclaration();
}
