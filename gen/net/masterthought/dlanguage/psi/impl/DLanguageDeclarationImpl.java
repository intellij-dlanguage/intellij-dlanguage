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

public class DLanguageDeclarationImpl extends ASTWrapperPsiElement implements DLanguageDeclaration {

  public DLanguageDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitDeclaration(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAggregateDeclaration getAggregateDeclaration() {
    return findChildByClass(DLanguageAggregateDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageAliasDeclaration getAliasDeclaration() {
    return findChildByClass(DLanguageAliasDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageEnumDeclaration getEnumDeclaration() {
    return findChildByClass(DLanguageEnumDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageFuncDeclaration getFuncDeclaration() {
    return findChildByClass(DLanguageFuncDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageImportDeclaration getImportDeclaration() {
    return findChildByClass(DLanguageImportDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageVarDeclarations getVarDeclarations() {
    return findChildByClass(DLanguageVarDeclarations.class);
  }

}
