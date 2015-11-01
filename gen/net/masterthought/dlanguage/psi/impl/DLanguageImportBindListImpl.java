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

public class DLanguageImportBindListImpl extends ASTWrapperPsiElement implements DLanguageImportBindList {

  public DLanguageImportBindListImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitImportBindList(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageImportBind getImportBind() {
    return findNotNullChildByClass(DLanguageImportBind.class);
  }

  @Override
  @Nullable
  public DLanguageImportBindList getImportBindList() {
    return findChildByClass(DLanguageImportBindList.class);
  }

  @Override
  @Nullable
  public PsiElement getOpComma() {
    return findChildByType(OP_COMMA);
  }

}
