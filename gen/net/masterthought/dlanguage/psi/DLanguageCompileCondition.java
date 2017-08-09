package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageCompileCondition extends PsiElement {
    @Nullable
    DLanguageVersionCondition getVersionCondition();

    @Nullable
    DLanguageDebugCondition getDebugCondition();

    @Nullable
    DLanguageStaticIfCondition getStaticIfCondition();
}
