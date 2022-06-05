package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface DLanguageMissingFunctionBody extends PsiElement {

    @Nullable
    List<DLanguageFunctionContract> getFunctionContracts();

    @Nullable
    PsiElement getSEMICOLON();
}
