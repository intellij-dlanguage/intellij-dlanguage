package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public interface DLanguageKeyValuePairs extends PsiElement {

    @NotNull
    public List<PsiElement> getOP_COMMAs();

    @NotNull
    public List<DLanguageKeyValuePair> getKeyValuePairs();
}
