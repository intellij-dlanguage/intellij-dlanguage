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

public class DLanguageDeallocatorImpl extends ASTWrapperPsiElement implements DLanguageDeallocator {

  public DLanguageDeallocatorImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitDeallocator(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageFunctionBody getFunctionBody() {
    return PsiTreeUtil.getChildOfType(this, DLanguageFunctionBody.class);
  }

  @Override
  @NotNull
  public DLanguageParameters getParameters() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageParameters.class));
  }

  @Override
  @NotNull
  public PsiElement getKwDelete() {
    return notNullChild(findChildByType(KW_DELETE));
  }

  @Override
  @Nullable
  public PsiElement getOpScolon() {
    return findChildByType(OP_SCOLON);
  }

}
