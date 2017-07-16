package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DLanguageFunctionDeclarationStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageFunctionDeclaration extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageFunctionDeclarationStub> {
    @Nullable
    public DLanguageType getType();

    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    public DLanguageParameters getParameters();

    @Nullable
    public DLanguageConstraint getConstraint();

    @Nullable
    public DLanguageFunctionBody getFunctionBody();
}
