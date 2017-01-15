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

public class DLanguageAsmExpImpl extends ASTWrapperPsiElement implements DLanguageAsmExp {

  public DLanguageAsmExpImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitAsmExp(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<DLanguageAsmExp> getAsmExpList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAsmExp.class);
  }

  @Override
  @NotNull
  public DLanguageAsmLogOrExp getAsmLogOrExp() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageAsmLogOrExp.class));
  }

  @Override
  @Nullable
  public PsiElement getOpColon() {
    return findChildByType(OP_COLON);
  }

  @Override
  @Nullable
  public PsiElement getOpQuest() {
    return findChildByType(OP_QUEST);
  }

}
