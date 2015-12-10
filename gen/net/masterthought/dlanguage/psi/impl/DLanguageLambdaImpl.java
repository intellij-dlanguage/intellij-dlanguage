// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;

import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import net.masterthought.dlanguage.psi.*;

public class DLanguageLambdaImpl extends ASTWrapperPsiElement implements DLanguageLambda {

  public DLanguageLambdaImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitLambda(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageAssignExpression getAssignExpression() {
    return findNotNullChildByClass(DLanguageAssignExpression.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return findChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageParameterAttributes getParameterAttributes() {
    return findChildByClass(DLanguageParameterAttributes.class);
  }

  @Override
  @Nullable
  public DLanguageParameterMemberAttributes getParameterMemberAttributes() {
    return findChildByClass(DLanguageParameterMemberAttributes.class);
  }

  @Override
  @Nullable
  public DLanguageType getType() {
    return findChildByClass(DLanguageType.class);
  }

  @Override
  @Nullable
  public PsiElement getKwDelegate() {
    return findChildByType(KW_DELEGATE);
  }

  @Override
  @Nullable
  public PsiElement getKwFunction() {
    return findChildByType(KW_FUNCTION);
  }

  @Override
  @NotNull
  public PsiElement getOpLambdaArrow() {
    return findNotNullChildByType(OP_LAMBDA_ARROW);
  }

}
