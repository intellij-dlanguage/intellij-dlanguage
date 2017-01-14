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

public class DLanguageConstructorImpl extends ASTWrapperPsiElement implements DLanguageConstructor {

  public DLanguageConstructorImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitConstructor(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageConstructorTemplate getConstructorTemplate() {
    return PsiTreeUtil.getChildOfType(this, DLanguageConstructorTemplate.class);
  }

  @Override
  @Nullable
  public DLanguageFunctionBody getFunctionBody() {
    return PsiTreeUtil.getChildOfType(this, DLanguageFunctionBody.class);
  }

  @Override
  @Nullable
  public DLanguageMemberFunctionAttributes getMemberFunctionAttributes() {
    return PsiTreeUtil.getChildOfType(this, DLanguageMemberFunctionAttributes.class);
  }

  @Override
  @Nullable
  public DLanguageParameters getParameters() {
    return PsiTreeUtil.getChildOfType(this, DLanguageParameters.class);
  }

  @Override
  @Nullable
  public PsiElement getKwThis() {
    return findChildByType(KW_THIS);
  }

  @Override
  @Nullable
  public PsiElement getOpScolon() {
    return findChildByType(OP_SCOLON);
  }

}
