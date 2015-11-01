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

public class DLanguageAggregateDeclarationImpl extends ASTWrapperPsiElement implements DLanguageAggregateDeclaration {

  public DLanguageAggregateDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAggregateDeclaration(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageClassDeclaration getClassDeclaration() {
    return findChildByClass(DLanguageClassDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageInterfaceDeclaration getInterfaceDeclaration() {
    return findChildByClass(DLanguageInterfaceDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageStructDeclaration getStructDeclaration() {
    return findChildByClass(DLanguageStructDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageUnionDeclaration getUnionDeclaration() {
    return findChildByClass(DLanguageUnionDeclaration.class);
  }

}
