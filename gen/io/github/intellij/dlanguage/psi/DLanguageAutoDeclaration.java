
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangAutoDeclarationPart;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAutoDeclaration extends PsiElement {

    @NotNull
    List<DLanguageStorageClass> getStorageClasss();

    @NotNull
    List<PsiElement> getOP_COMMAs();

    @Nullable
    PsiElement getOP_SCOLON();

    @NotNull
    List<DlangAutoDeclarationPart> getAutoDeclarationParts();
}
