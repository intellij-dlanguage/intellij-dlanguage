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

public class DLanguageAggregateDeclarationImpl extends ASTWrapperPsiElement implements DLanguageAggregateDeclaration {

  public DLanguageAggregateDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitAggregateDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageClassDeclaration getClassDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageClassDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageInterfaceDeclaration getInterfaceDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageInterfaceDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageStructDeclaration getStructDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageStructDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageUnionDeclaration getUnionDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageUnionDeclaration.class);
  }

  public boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place) {
    return DPsiImplUtil.processDeclarations(this, processor, state, lastParent, place);
  }

}
