package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageType extends PsiElement {
    @Nullable
    public DLanguageAttribute getAttribute();

    @Nullable
    public DLanguageType_2 getType_2();

    @NotNull
    public List<DLanguageTypeSuffix> getTypeSuffixs();
}
