package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangEnumMember;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageEnumBody extends PsiElement {

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_BRACES_RIGHT();

    @Nullable
    PsiElement getOP_BRACES_LEFT();

    @NotNull
    List<DlangEnumMember> getEnumMembers();

    @NotNull
    List<PsiElement> getOP_COMMAs();

    @Nullable
    PsiElement getOP_SCOLON();

}
