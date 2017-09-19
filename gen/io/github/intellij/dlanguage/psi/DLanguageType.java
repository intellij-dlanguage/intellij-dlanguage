package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageType extends PsiElement {
    @Nullable
    DLanguageAttribute getAttribute();

    @Nullable
    DLanguageType_2 getType_2();

    @NotNull
    List<DLanguageTypeSuffix> getTypeSuffixs();
}
