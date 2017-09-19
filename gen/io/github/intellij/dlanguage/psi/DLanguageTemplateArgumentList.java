package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public interface DLanguageTemplateArgumentList extends PsiElement {
    @NotNull
    List<PsiElement> getOP_COMMAs();

    @NotNull
    List<DLanguageTemplateArgument> getTemplateArguments();
}
