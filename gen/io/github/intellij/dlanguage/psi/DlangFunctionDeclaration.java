
package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.scope.PsiScopeProcessor;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import io.github.intellij.dlanguage.stubs.DlangFunctionDeclarationStub;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DlangFunctionDeclaration extends PsiElement, DNamedElement,
    StubBasedPsiElement<DlangFunctionDeclarationStub> {

    @Nullable
    DLanguageType getType();

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    DLanguageParameters getParameters();

    @Nullable
    DLanguageConstraint getConstraint();

    @Nullable
    DLanguageFunctionBody getFunctionBody();

    @NotNull
    List<DLanguageMemberFunctionAttribute> getMemberFunctionAttributes();

    @Override
    default boolean processDeclarations(@NotNull PsiScopeProcessor processor,
        @NotNull ResolveState state,
        PsiElement lastParent,
        @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE
            .processDeclarations(this, processor, state, lastParent, place);
    }
}
