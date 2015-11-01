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

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitFunctionLiteral(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageFunctionLiteralBody getFunctionLiteralBody() {
    return findChildByClass(DLanguageFunctionLiteralBody.class);
  }

  @Override
  @Nullable
  public DLanguageLambda getLambda() {
    return findChildByClass(DLanguageLambda.class);
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

}
