package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageCatches extends PsiElement {

    @Nullable
    DLanguageLastCatch getLastCatch();

    @Nullable
    DlangCatch getCatch();
}
