
package io.github.intellij.dlanguage.psi.named;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.Declaration;
import io.github.intellij.dlanguage.psi.interfaces.FunctionBody;
import io.github.intellij.dlanguage.stubs.DLanguageFunctionDeclarationStub;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageFunctionDeclaration extends PsiElement, DNamedElement, Declaration,
    StubBasedPsiElement<DLanguageFunctionDeclarationStub> {

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
