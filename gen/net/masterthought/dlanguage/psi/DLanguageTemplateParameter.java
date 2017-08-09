package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.stubs.DLanguageTemplateParameterStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateParameter extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageTemplateParameterStub> {
    @Nullable
    DLanguageTemplateAliasParameter getTemplateAliasParameter();

    @Nullable
    DLanguageTemplateTupleParameter getTemplateTupleParameter();

    @Nullable
    DLanguageTemplateTypeParameter getTemplateTypeParameter();

    @Nullable
    DLanguageTemplateThisParameter getTemplateThisParameter();

    @Nullable
    DLanguageTemplateValueParameter getTemplateValueParameter();
}
