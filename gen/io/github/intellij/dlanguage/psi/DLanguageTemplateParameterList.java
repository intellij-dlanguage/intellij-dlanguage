package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public interface DLanguageTemplateParameterList extends PsiElement {

    @NotNull
    List<DLanguageTemplateParameter> getTemplateParameters();

    @NotNull
    List<PsiElement> getOP_COMMAs();

}
