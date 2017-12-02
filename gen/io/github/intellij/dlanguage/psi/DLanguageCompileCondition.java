package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageCompileCondition extends PsiElement {

    @Nullable
    public DLanguageVersionCondition getVersionCondition();

    @Nullable
    public DLanguageDebugCondition getDebugCondition();

    @Nullable
    public DLanguageStaticIfCondition getStaticIfCondition();
}
