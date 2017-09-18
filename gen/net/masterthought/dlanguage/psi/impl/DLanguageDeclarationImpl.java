package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.resolve.ScopeProcessorImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_BRACES_LEFT;
import static net.masterthought.dlanguage.psi.DlangTypes.OP_BRACES_RIGHT;


public class DLanguageDeclarationImpl extends ASTWrapperPsiElement implements DLanguageDeclaration {
    public DLanguageDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageAliasThisDeclaration getAliasThisDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAliasThisDeclaration.class);
    }

    @Nullable
    public DLanguageAliasDeclaration getAliasDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAliasDeclaration.class);
    }

    @Nullable
    public DLanguageClassDeclaration getClassDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageClassDeclaration.class);
    }

    @Nullable
    public DLanguageConditionalDeclaration getConditionalDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageConditionalDeclaration.class);
    }

    @Nullable
    public DLanguageConstructor getConstructor() {
        return PsiTreeUtil.getChildOfType(this, DLanguageConstructor.class);
    }

    @Nullable
    public DLanguageDestructor getDestructor() {
        return PsiTreeUtil.getChildOfType(this, DLanguageDestructor.class);
    }

    @Nullable
    public DLanguageAnonymousEnumDeclaration getAnonymousEnumDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAnonymousEnumDeclaration.class);
    }

    @Nullable
    public DLanguageEponymousTemplateDeclaration getEponymousTemplateDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageEponymousTemplateDeclaration.class);
    }

    @Nullable
    public DlangEnumDeclaration getEnumDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DlangEnumDeclaration.class);
    }

    @Nullable
    public DLanguageImportDeclaration getImportDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageImportDeclaration.class);
    }

    @Nullable
    public DLanguageInterfaceDeclaration getInterfaceDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageInterfaceDeclaration.class);
    }

    @Nullable
    public DLanguageMixinTemplateDeclaration getMixinTemplateDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageMixinTemplateDeclaration.class);
    }

    @Nullable
    public DLanguageMixinDeclaration getMixinDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageMixinDeclaration.class);
    }

    @Nullable
    public DLanguagePragmaDeclaration getPragmaDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguagePragmaDeclaration.class);
    }

    @Nullable
    public DLanguageSharedStaticConstructor getSharedStaticConstructor() {
        return PsiTreeUtil.getChildOfType(this, DLanguageSharedStaticConstructor.class);
    }

    @Nullable
    public DLanguageSharedStaticDestructor getSharedStaticDestructor() {
        return PsiTreeUtil.getChildOfType(this, DLanguageSharedStaticDestructor.class);
    }

    @Nullable
    public DLanguageStaticConstructor getStaticConstructor() {
        return PsiTreeUtil.getChildOfType(this, DLanguageStaticConstructor.class);
    }

    @Nullable
    public DLanguageStaticDestructor getStaticDestructor() {
        return PsiTreeUtil.getChildOfType(this, DLanguageStaticDestructor.class);
    }

    @Nullable
    public DLanguageStaticAssertDeclaration getStaticAssertDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageStaticAssertDeclaration.class);
    }

    @Nullable
    public DlangStructDeclaration getStructDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DlangStructDeclaration.class);
    }

    @Nullable
    public DlangTemplateDeclaration getTemplateDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DlangTemplateDeclaration.class);
    }

    @Nullable
    public DlangUnionDeclaration getUnionDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DlangUnionDeclaration.class);
    }

    @Nullable
    public DLanguageInvariant getInvariant() {
        return PsiTreeUtil.getChildOfType(this, DLanguageInvariant.class);
    }

    @Nullable
    public DLanguageUnittest getUnittest() {
        return PsiTreeUtil.getChildOfType(this, DLanguageUnittest.class);
    }

    @Nullable
    public DLanguageVersionSpecification getVersionSpecification() {
        return PsiTreeUtil.getChildOfType(this, DLanguageVersionSpecification.class);
    }

    @Nullable
    public DLanguageDebugSpecification getDebugSpecification() {
        return PsiTreeUtil.getChildOfType(this, DLanguageDebugSpecification.class);
    }

    @NotNull
    public List<DLanguageAttribute> getAttributes() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAttribute.class);
    }

    @NotNull
    public List<DLanguageDeclaration> getDeclarations() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageDeclaration.class);
    }

    @Nullable
    public PsiElement getOP_BRACES_RIGHT() {
        return findChildByType(OP_BRACES_RIGHT);
    }

    @Nullable
    public PsiElement getOP_BRACES_LEFT() {
        return findChildByType(OP_BRACES_LEFT);
    }

    @Nullable
    public DLanguageFunctionDeclaration getFunctionDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageFunctionDeclaration.class);
    }

    @Nullable
    public DLanguageVariableDeclaration getVariableDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageVariableDeclaration.class);
    }

    @Nullable
    public DLanguageAttributeDeclaration getAttributeDeclaration() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAttributeDeclaration.class);
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor,
                                       @NotNull ResolveState state,
                                       PsiElement lastParent,
                                       @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }
}
