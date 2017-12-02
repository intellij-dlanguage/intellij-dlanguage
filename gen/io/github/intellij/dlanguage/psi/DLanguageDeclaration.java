package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageDeclaration extends PsiElement {

    @Nullable
    public DLanguageAliasThisDeclaration getAliasThisDeclaration();

    @Nullable
    public DLanguageAliasDeclaration getAliasDeclaration();

    @Nullable
    public DLanguageClassDeclaration getClassDeclaration();

    @Nullable
    public DLanguageConditionalDeclaration getConditionalDeclaration();

    @Nullable
    public DLanguageConstructor getConstructor();

    @Nullable
    public DLanguageDestructor getDestructor();

    @Nullable
    public DLanguageAnonymousEnumDeclaration getAnonymousEnumDeclaration();

    @Nullable
    public DLanguageEponymousTemplateDeclaration getEponymousTemplateDeclaration();

    @Nullable
    public DLanguageEnumDeclaration getEnumDeclaration();

    @Nullable
    public DLanguageImportDeclaration getImportDeclaration();

    @Nullable
    public DLanguageInterfaceDeclaration getInterfaceDeclaration();

    @Nullable
    public DLanguageMixinTemplateDeclaration getMixinTemplateDeclaration();

    @Nullable
    public DLanguageMixinDeclaration getMixinDeclaration();

    @Nullable
    public DLanguagePragmaDeclaration getPragmaDeclaration();

    @Nullable
    public DLanguageSharedStaticConstructor getSharedStaticConstructor();

    @Nullable
    public DLanguageSharedStaticDestructor getSharedStaticDestructor();

    @Nullable
    public DLanguageStaticConstructor getStaticConstructor();

    @Nullable
    public DLanguageStaticDestructor getStaticDestructor();

    @Nullable
    public DLanguageStaticAssertDeclaration getStaticAssertDeclaration();

    @Nullable
    public DLanguageStructDeclaration getStructDeclaration();

    @Nullable
    public DLanguageTemplateDeclaration getTemplateDeclaration();

    @Nullable
    public DLanguageUnionDeclaration getUnionDeclaration();

    @Nullable
    public DLanguageInvariant getInvariant();

    @Nullable
    public DLanguageUnittest getUnittest();

    @Nullable
    public DLanguageVersionSpecification getVersionSpecification();

    @Nullable
    public DLanguageDebugSpecification getDebugSpecification();

    @NotNull
    public List<DLanguageAttribute> getAttributes();

    @NotNull
    public List<DLanguageDeclaration> getDeclarations();

    @Nullable
    public PsiElement getOP_BRACES_RIGHT();

    @Nullable
    public PsiElement getOP_BRACES_LEFT();

    @Nullable
    public DLanguageFunctionDeclaration getFunctionDeclaration();

    @Nullable
    public DLanguageVariableDeclaration getVariableDeclaration();

    @Nullable
    public DLanguageAttributeDeclaration getAttributeDeclaration();
}
