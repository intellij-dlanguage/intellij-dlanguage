package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.scope.PsiScopeProcessor;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageConstructor extends PsiElement, DNamedElement,
    StubBasedPsiElement<DLanguageConstructorStub> {

    @Nullable
    DLanguageFunctionBody getFunctionBody();

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

    @Override
    default boolean processDeclarations(@NotNull PsiScopeProcessor processor,
        @NotNull ResolveState state,
        PsiElement lastParent,
        @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE
            .processDeclarations(this, processor, state, lastParent, place);
    }
}
