// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import net.masterthought.dlanguage.psi.*;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;

public class DLanguageDeclarationImpl extends ASTWrapperPsiElement implements DLanguageDeclaration {

  public DLanguageDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAggregateDeclaration getAggregateDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAggregateDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageAliasDeclaration getAliasDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAliasDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageEnumDeclaration getEnumDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageEnumDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageFuncDeclaration getFuncDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageFuncDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageImportDeclaration getImportDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageImportDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateDeclaration getTemplateDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageVarDeclarations getVarDeclarations() {
    return PsiTreeUtil.getChildOfType(this, DLanguageVarDeclarations.class);
  }

  @Override
  @Nullable
  public DLanguageVarFuncDeclaration getVarFuncDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageVarFuncDeclaration.class);
  }

  public boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place) {
    return DPsiImplUtil.processDeclarations(this, processor, state, lastParent, place);
  }

}
