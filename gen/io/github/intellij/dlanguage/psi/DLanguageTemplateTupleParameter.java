package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateTupleParameter extends PsiElement {

    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_TRIPLEDOT();

}
