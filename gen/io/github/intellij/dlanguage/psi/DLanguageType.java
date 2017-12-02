package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageType extends PsiElement {

    @Nullable
    public DLanguageAttribute getAttribute();

    @Nullable
    public DLanguageType_2 getType_2();

    @NotNull
    public List<DLanguageTypeSuffix> getTypeSuffixs();
}
