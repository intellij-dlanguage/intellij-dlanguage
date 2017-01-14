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

public class DLanguageAutoDeclarationXImpl extends ASTWrapperPsiElement implements DLanguageAutoDeclarationX {

  public DLanguageAutoDeclarationXImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitAutoDeclarationX(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAutoDeclarationX getAutoDeclarationX() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAutoDeclarationX.class);
  }

  @Override
  @NotNull
  public DLanguageAutoDeclarationY getAutoDeclarationY() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageAutoDeclarationY.class));
  }

  @Override
  @Nullable
  public PsiElement getOpComma() {
    return findChildByType(OP_COMMA);
  }

}
