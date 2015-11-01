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

public class DLanguageParameterListImpl extends ASTWrapperPsiElement implements DLanguageParameterList {

  public DLanguageParameterListImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitParameterList(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageParameter getParameter() {
    return findChildByClass(DLanguageParameter.class);
  }

  @Override
  @Nullable
  public DLanguageParameterList getParameterList() {
    return findChildByClass(DLanguageParameterList.class);
  }

  @Override
  @Nullable
  public PsiElement getOpComma() {
    return findChildByType(OP_COMMA);
  }

  @Override
  @Nullable
  public PsiElement getOpTripledot() {
    return findChildByType(OP_TRIPLEDOT);
  }

}
