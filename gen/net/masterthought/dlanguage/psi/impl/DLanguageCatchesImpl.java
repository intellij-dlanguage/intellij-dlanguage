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

public class DLanguageCatchesImpl extends ASTWrapperPsiElement implements DLanguageCatches {

  public DLanguageCatchesImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitCatches(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<DLanguageCatch> getCatchList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageCatch.class);
  }

  @Override
  @Nullable
  public DLanguageLastCatch getLastCatch() {
    return PsiTreeUtil.getChildOfType(this, DLanguageLastCatch.class);
  }

}
