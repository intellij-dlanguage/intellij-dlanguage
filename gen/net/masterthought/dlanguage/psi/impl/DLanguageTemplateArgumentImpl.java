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

public class DLanguageTemplateArgumentImpl extends ASTWrapperPsiElement implements DLanguageTemplateArgument {

  public DLanguageTemplateArgumentImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitTemplateArgument(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAssignExpression getAssignExpression() {
    return findChildByClass(DLanguageAssignExpression.class);
  }

  @Override
  @Nullable
  public DLanguageSymbol getSymbol() {
    return findChildByClass(DLanguageSymbol.class);
  }

  @Override
  @Nullable
  public DLanguageType getType() {
    return findChildByClass(DLanguageType.class);
  }

}
