package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public interface DLanguageTemplateArgumentList extends PsiElement {

    @NotNull
    List<PsiElement> getOP_COMMAs();

    @NotNull
    List<DLanguageTemplateArgument> getTemplateArguments();
}
