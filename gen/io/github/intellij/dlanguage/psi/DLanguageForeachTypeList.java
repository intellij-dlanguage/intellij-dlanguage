package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangForeachType;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public interface DLanguageForeachTypeList extends PsiElement {

    @NotNull
    List<DlangForeachType> getForeachTypes();

    @NotNull
    List<PsiElement> getOP_COMMAs();

}
