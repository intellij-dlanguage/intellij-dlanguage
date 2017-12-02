package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageEnumBody extends PsiElement {

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_BRACES_RIGHT();

    @Nullable
    public PsiElement getOP_BRACES_LEFT();

    @NotNull
    public List<DLanguageEnumMember> getEnumMembers();

    @NotNull
    public List<PsiElement> getOP_COMMAs();

    @Nullable
    public PsiElement getOP_SCOLON();

}
