package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DLanguageVersionSpecification;
import io.github.intellij.dlanguage.psi.named.DlangConstructor;
import io.github.intellij.dlanguage.psi.named.DlangDestructor;
import io.github.intellij.dlanguage.psi.named.DlangEnumDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangFunctionDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangSharedStaticConstructor;
import io.github.intellij.dlanguage.psi.named.DlangSharedStaticDestructor;
import io.github.intellij.dlanguage.psi.named.DlangStaticConstructor;
import io.github.intellij.dlanguage.psi.named.DlangStaticDestructor;
import io.github.intellij.dlanguage.psi.named.DlangStructDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangTemplateDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangUnionDeclaration;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageDeclaration extends PsiElement {

    @Nullable
    DLanguageAliasThisDeclaration getAliasThisDeclaration();

    @Nullable
    DLanguageAliasDeclaration getAliasDeclaration();

    @Nullable
    DLanguageClassDeclaration getClassDeclaration();

    @Nullable
    DLanguageConditionalDeclaration getConditionalDeclaration();

    @Nullable
    DlangConstructor getConstructor();

    @Nullable
    DlangDestructor getDestructor();

    @Nullable
    DLanguageAnonymousEnumDeclaration getAnonymousEnumDeclaration();

    @Nullable
    DLanguageEponymousTemplateDeclaration getEponymousTemplateDeclaration();

    @Nullable
    DlangEnumDeclaration getEnumDeclaration();

    @Nullable
    DLanguageImportDeclaration getImportDeclaration();

    @Nullable
    DLanguageInterfaceDeclaration getInterfaceDeclaration();

    @Nullable
    DLanguageMixinTemplateDeclaration getMixinTemplateDeclaration();

    @Nullable
    DLanguageMixinDeclaration getMixinDeclaration();

    @Nullable
    DLanguagePragmaDeclaration getPragmaDeclaration();

    @Nullable
    DlangSharedStaticConstructor getSharedStaticConstructor();

    @Nullable
    DlangSharedStaticDestructor getSharedStaticDestructor();

    @Nullable
    DlangStaticConstructor getStaticConstructor();

    @Nullable
    DlangStaticDestructor getStaticDestructor();

    @Nullable
    DLanguageStaticAssertDeclaration getStaticAssertDeclaration();

    @Nullable
    DlangStructDeclaration getStructDeclaration();

    @Nullable
    DlangTemplateDeclaration getTemplateDeclaration();

    @Nullable
    DlangUnionDeclaration getUnionDeclaration();

    @Nullable
    DLanguageInvariant getInvariant();

    @Nullable
    DLanguageUnittest getUnittest();

    @Nullable
    DLanguageVersionSpecification getVersionSpecification();

    @Nullable
    DLanguageDebugSpecification getDebugSpecification();

    @NotNull
    List<DLanguageAttribute> getAttributes();

    @NotNull
    List<DLanguageDeclaration> getDeclarations();

    @Nullable
    PsiElement getOP_BRACES_RIGHT();

    @Nullable
    PsiElement getOP_BRACES_LEFT();

    @Nullable
    DlangFunctionDeclaration getFunctionDeclaration();

    @Nullable
    DLanguageVariableDeclaration getVariableDeclaration();

    @Nullable
    DLanguageAttributeDeclaration getAttributeDeclaration();
}
