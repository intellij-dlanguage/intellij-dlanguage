package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.scope.PsiScopeProcessor;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.HasMembers;
import net.masterthought.dlanguage.resolve.ScopeProcessorImpl;
import net.masterthought.dlanguage.stubs.DLanguageEnumDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//import net.masterthought.dlanguage.types.Type;


public interface DLanguageEnumDeclaration extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageEnumDeclarationStub>, HasMembers<DLanguageEnumDeclarationStub> {
    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    PsiElement getKW_ENUM();

    @Nullable
    DLanguageType getType();

    @Nullable
    DLanguageEnumBody getEnumBody();



    @Override
    default boolean processDeclarations(@NotNull PsiScopeProcessor processor,
                                        @NotNull ResolveState state,
                                        PsiElement lastParent,
                                        @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }

//    @NotNull
//    Type getMemtype();
}
