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

public class DLanguageForeachTypeListImpl extends ASTWrapperPsiElement implements DLanguageForeachTypeList {

  public DLanguageForeachTypeListImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitForeachTypeList(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageForeachType getForeachType() {
    return findNotNullChildByClass(DLanguageForeachType.class);
  }

  @Override
  @Nullable
  public DLanguageForeachTypeList getForeachTypeList() {
    return findChildByClass(DLanguageForeachTypeList.class);
  }

  @Override
  @Nullable
  public PsiElement getOpComma() {
    return findChildByType(OP_COMMA);
  }

}
