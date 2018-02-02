
package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageTemplateAliasParameter;
import io.github.intellij.dlanguage.psi.DLanguageTemplateThisParameter;
import io.github.intellij.dlanguage.psi.DLanguageTemplateTupleParameter;
import io.github.intellij.dlanguage.psi.DLanguageTemplateTypeParameter;
import io.github.intellij.dlanguage.psi.DLanguageTemplateValueParameter;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.stubs.DlangTemplateParameterStub;
import org.jetbrains.annotations.Nullable;


public interface DlangTemplateParameter extends PsiElement, DNamedElement,
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
