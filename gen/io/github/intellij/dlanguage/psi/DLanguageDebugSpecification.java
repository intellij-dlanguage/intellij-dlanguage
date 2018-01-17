package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import org.jetbrains.annotations.Nullable;


public interface DLanguageDebugSpecification extends PsiElement {

    @Nullable
    public PsiElement getKW_DEBUG();

    @Nullable
    public PsiElement getOP_EQ();

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public PsiElement getINTEGER_LITERAL();

    @Nullable
    public PsiElement getOP_SCOLON();

}
