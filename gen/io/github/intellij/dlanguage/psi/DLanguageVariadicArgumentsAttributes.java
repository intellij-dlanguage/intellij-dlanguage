package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface DLanguageVariadicArgumentsAttributes extends PsiElement {

    @Nullable
    List<PsiElement> getOP_TRIPLEDOTs();

    @Nullable
    List<DLanguageVariadicArgumentsAttribute> getVariadicArgumentsAttributes();
}
