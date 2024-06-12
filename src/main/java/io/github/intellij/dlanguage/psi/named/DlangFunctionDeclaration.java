
package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.scope.PsiScopeProcessor;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.Declaration;
import io.github.intellij.dlanguage.psi.interfaces.FunctionBody;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import io.github.intellij.dlanguage.stubs.DlangFunctionDeclarationStub;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DlangFunctionDeclaration extends PsiElement, DNamedElement, Declaration,
    StubBasedPsiElement<DlangFunctionDeclarationStub> {

    @Nullable
    DLanguageBasicType getBasicType();

    @NotNull
    List<DLanguageTypeSuffix> getTypeSuffixes();

    @Nullable
    PsiElement getIdentifier();

    @Nullable
    DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    DLanguageParameters getParameters();

    @Nullable
    DLanguageConstraint getConstraint();

    @Nullable
    FunctionBody getFunctionBody();

    @NotNull
    List<DLanguageMemberFunctionAttribute> getMemberFunctionAttributes();

    @NotNull
    List<DLanguageStorageClass> getStorageClasses();

    @Override
    default boolean processDeclarations(@NotNull PsiScopeProcessor processor,
        @NotNull ResolveState state,
        PsiElement lastParent,
        @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE
            .processDeclarations(this, processor, state, lastParent, place);
    }

    default DLanguageStorageClass getAutoElem() {
        for (DLanguageStorageClass storageClass : getStorageClasses()) {
            if(storageClass.getKW_AUTO() != null)
                return storageClass;
        }
        return null;
    }

    default boolean isAuto() {
        return getAutoElem() != null;

    }
}
