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

public class DLanguageFunctionLiteralImpl extends ASTWrapperPsiElement implements DLanguageFunctionLiteral {

  public DLanguageFunctionLiteralImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitFunctionLiteral(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageFunctionLiteralBody getFunctionLiteralBody() {
    return PsiTreeUtil.getChildOfType(this, DLanguageFunctionLiteralBody.class);
  }

  @Override
  @Nullable
  public DLanguageLambda getLambda() {
    return PsiTreeUtil.getChildOfType(this, DLanguageLambda.class);
  }

  @Override
  @Nullable
  public DLanguageParameterAttributes getParameterAttributes() {
    return PsiTreeUtil.getChildOfType(this, DLanguageParameterAttributes.class);
  }

  @Override
  @Nullable
  public DLanguageParameterMemberAttributes getParameterMemberAttributes() {
    return PsiTreeUtil.getChildOfType(this, DLanguageParameterMemberAttributes.class);
  }

  @Override
  @Nullable
  public DLanguageType getType() {
    return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
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

}
