package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public interface DLanguageTypeConstructors extends PsiElement {
    @NotNull
    List<DLanguageTypeConstructor> getTypeConstructors();
}
