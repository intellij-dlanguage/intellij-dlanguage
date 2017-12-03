package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.DlangTemplateParameterStub;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateParameter extends PsiElement, DNamedElement,
    StubBasedPsiElement<DlangTemplateParameterStub> {

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
