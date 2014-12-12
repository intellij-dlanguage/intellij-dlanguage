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

public class DLanguageMethodDeclarationImpl extends ASTWrapperPsiElement implements DLanguageMethodDeclaration {

  public DLanguageMethodDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitMethodDeclaration(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageIdentifier getIdentifier() {
    return findNotNullChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @NotNull
  public DLanguageModifier getModifier() {
    return findNotNullChildByClass(DLanguageModifier.class);
  }

  @Override
  @Nullable
  public DLanguageParameterList getParameterList() {
    return findChildByClass(DLanguageParameterList.class);
  }

  @Override
  @Nullable
  public DLanguageStatementBlock getStatementBlock() {
    return findChildByClass(DLanguageStatementBlock.class);
  }

  @Override
  @NotNull
  public DLanguageType getType() {
    return findNotNullChildByClass(DLanguageType.class);
  }

}
