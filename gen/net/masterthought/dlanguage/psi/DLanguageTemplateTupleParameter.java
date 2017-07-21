package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTemplateTupleParameter extends PsiElement {
    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_TRIPLEDOT();

}
