package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public interface DLanguageStructMemberInitializers extends PsiElement {
    @NotNull
    List<DLanguageStructMemberInitializer> getStructMemberInitializers();

    @NotNull
    List<PsiElement> getOP_COMMAs();

}
