package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public interface DLanguageTypeConstructors extends PsiElement {

    @NotNull
    public List<DLanguageTypeConstructor> getTypeConstructors();
}
