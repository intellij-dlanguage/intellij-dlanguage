package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public interface DLanguageForeachTypeList extends PsiElement {

    @NotNull
    List<DlangForeachType> getForeachTypes();

    @NotNull
    List<PsiElement> getOP_COMMAs();

}
