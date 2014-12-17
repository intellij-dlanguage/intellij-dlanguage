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

public class DLanguageFunctionAttributeImpl extends ASTWrapperPsiElement implements DLanguageFunctionAttribute {

  public DLanguageFunctionAttributeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitFunctionAttribute(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAtAttribute getAtAttribute() {
    return findChildByClass(DLanguageAtAttribute.class);
  }

  @Override
  @Nullable
  public PsiElement getNothrow() {
    return findChildByType(NOTHROW);
  }

  @Override
  @Nullable
  public PsiElement getPure() {
    return findChildByType(PURE);
  }

}
