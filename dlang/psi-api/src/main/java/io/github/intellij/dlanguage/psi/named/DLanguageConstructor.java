
package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.DLanguageMemberFunctionAttribute;
import io.github.intellij.dlanguage.psi.DLanguageParameters;
import io.github.intellij.dlanguage.psi.DLanguageTemplateParameters;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.Declaration;
import io.github.intellij.dlanguage.psi.interfaces.FunctionBody;
import io.github.intellij.dlanguage.stubs.DLanguageConstructorStub;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageConstructor extends PsiElement, DNamedElement, Declaration,
    StubBasedPsiElement<DLanguageConstructorStub> {

    @Nullable
    FunctionBody getFunctionBody();

    @Nullable
    PsiElement getOP_SCOLON();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getKW_THIS();

    @NotNull
    List<DLanguageMemberFunctionAttribute> getMemberFunctionAttributes();

    @Nullable
    DLanguageParameters getParameters();

    @Nullable
    DLanguageTemplateParameters getTemplateParameters();
}
