package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DLanguageEponymousTemplateDeclarationStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageEponymousTemplateDeclaration extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageEponymousTemplateDeclarationStub> {
    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    public PsiElement getOP_EQ();

    @Nullable
    public DLanguageType getType();

    @Nullable
    public PsiElement getOP_SCOLON();

    @Nullable
    public PsiElement getKW_ENUM();

    @Nullable
    public PsiElement getKW_ALIAS();

}
