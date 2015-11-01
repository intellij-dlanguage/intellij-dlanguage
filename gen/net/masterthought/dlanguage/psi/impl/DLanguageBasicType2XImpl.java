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

public class DLanguageBasicType2XImpl extends ASTWrapperPsiElement implements DLanguageBasicType2X {

  public DLanguageBasicType2XImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitBasicType2X(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAssignExpression getAssignExpression() {
    return findChildByClass(DLanguageAssignExpression.class);
  }

  @Override
  @Nullable
  public DLanguageFunctionAttributes getFunctionAttributes() {
    return findChildByClass(DLanguageFunctionAttributes.class);
  }

  @Override
  @Nullable
  public DLanguageMemberFunctionAttributes getMemberFunctionAttributes() {
    return findChildByClass(DLanguageMemberFunctionAttributes.class);
  }

  @Override
  @Nullable
  public DLanguageParameters getParameters() {
    return findChildByClass(DLanguageParameters.class);
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
  @Nullable
  public PsiElement getOpAsterisk() {
    return findChildByType(OP_ASTERISK);
  }

  @Override
  @Nullable
  public PsiElement getOpBracketLeft() {
    return findChildByType(OP_BRACKET_LEFT);
  }

  @Override
  @Nullable
  public PsiElement getOpBracketRight() {
    return findChildByType(OP_BRACKET_RIGHT);
  }

  @Override
  @Nullable
  public PsiElement getOpDdot() {
    return findChildByType(OP_DDOT);
  }

}
