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

public class DLanguageKeyValuePairsImpl extends ASTWrapperPsiElement implements DLanguageKeyValuePairs {

  public DLanguageKeyValuePairsImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitKeyValuePairs(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageKeyValuePair getKeyValuePair() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageKeyValuePair.class));
  }

  @Override
  @Nullable
  public DLanguageKeyValuePairs getKeyValuePairs() {
    return PsiTreeUtil.getChildOfType(this, DLanguageKeyValuePairs.class);
  }

  @Override
  @Nullable
  public PsiElement getOpComma() {
    return findChildByType(OP_COMMA);
  }

}
