package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateTupleParameter extends PsiElement {
    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_TRIPLEDOT();

}
