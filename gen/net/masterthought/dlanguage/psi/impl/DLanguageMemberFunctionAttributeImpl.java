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

public class DLanguageMemberFunctionAttributeImpl extends ASTWrapperPsiElement implements DLanguageMemberFunctionAttribute {

  public DLanguageMemberFunctionAttributeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitMemberFunctionAttribute(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageFunctionAttribute getFunctionAttribute() {
    return findChildByClass(DLanguageFunctionAttribute.class);
  }

  @Override
  @Nullable
  public PsiElement getKwConst() {
    return findChildByType(KW_CONST);
  }

  @Override
  @Nullable
  public PsiElement getKwImmutable() {
    return findChildByType(KW_IMMUTABLE);
  }

  @Override
  @Nullable
  public PsiElement getKwInout() {
    return findChildByType(KW_INOUT);
  }

  @Override
  @Nullable
  public PsiElement getKwShared() {
    return findChildByType(KW_SHARED);
  }

}
