package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DLanguageInterfaceOrClassStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageInterfaceOrClass extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageInterfaceOrClassStub> {
    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public DLanguageStructBody getStructBody();

    @Nullable
    public DLanguageConstraint getConstraint();

    @Nullable
    public DLanguageBaseClassList getBaseClassList();
}
