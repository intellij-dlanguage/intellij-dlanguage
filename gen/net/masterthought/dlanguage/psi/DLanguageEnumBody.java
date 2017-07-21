package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageEnumBody extends PsiElement {
    @Nullable
    public DLanguageIdentifier getIdentifier();

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
