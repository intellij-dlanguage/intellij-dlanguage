package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageStructInitializer extends PsiElement {
    @NotNull
    List<DLanguageStructMemberInitializers> getStructMemberInitializerss();

    @Nullable
    PsiElement getOP_BRACES_RIGHT();

    @Nullable
    PsiElement getOP_BRACES_LEFT();

}
