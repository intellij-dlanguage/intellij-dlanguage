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

public class DLanguageExpressionImpl extends ASTWrapperPsiElement implements DLanguageExpression {

  public DLanguageExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitExpression(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageArglist getArglist() {
    return findChildByClass(DLanguageArglist.class);
  }

  @Override
  @Nullable
  public DLanguageBitExpression getBitExpression() {
    return findChildByClass(DLanguageBitExpression.class);
  }

  @Override
  @Nullable
  public DLanguageCastingExpression getCastingExpression() {
    return findChildByClass(DLanguageCastingExpression.class);
  }

  @Override
  @Nullable
  public DLanguageClassName getClassName() {
    return findChildByClass(DLanguageClassName.class);
  }

  @Override
  @Nullable
  public DLanguageCreatingExpression getCreatingExpression() {
    return findChildByClass(DLanguageCreatingExpression.class);
  }

  @Override
  @Nullable
  public DLanguageExpression getExpression() {
    return findChildByClass(DLanguageExpression.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return findChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageInterfaceName getInterfaceName() {
    return findChildByClass(DLanguageInterfaceName.class);
  }

  @Override
  @Nullable
  public DLanguageLiteralExpression getLiteralExpression() {
    return findChildByClass(DLanguageLiteralExpression.class);
  }

  @Override
  @Nullable
  public DLanguageLogicalExpression getLogicalExpression() {
    return findChildByClass(DLanguageLogicalExpression.class);
  }

  @Override
  @Nullable
  public DLanguageNumericExpression getNumericExpression() {
    return findChildByClass(DLanguageNumericExpression.class);
  }

  @Override
  @Nullable
  public DLanguageStringExpression getStringExpression() {
    return findChildByClass(DLanguageStringExpression.class);
  }

  @Override
  @Nullable
  public DLanguageTestingExpression getTestingExpression() {
    return findChildByClass(DLanguageTestingExpression.class);
  }

}
