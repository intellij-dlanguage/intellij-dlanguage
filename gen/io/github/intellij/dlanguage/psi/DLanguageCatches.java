package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageCatches extends PsiElement {

    @Nullable
    public DLanguageLastCatch getLastCatch();

    @Nullable
    public DLanguageCatch getCatch();
}
