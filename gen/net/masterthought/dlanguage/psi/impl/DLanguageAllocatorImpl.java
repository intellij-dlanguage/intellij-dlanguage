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

public class DLanguageAllocatorImpl extends ASTWrapperPsiElement implements DLanguageAllocator {

  public DLanguageAllocatorImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAllocator(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageFunctionBody getFunctionBody() {
    return findChildByClass(DLanguageFunctionBody.class);
  }

  @Override
  @NotNull
  public DLanguageParameters getParameters() {
    return findNotNullChildByClass(DLanguageParameters.class);
  }

  @Override
  @NotNull
  public PsiElement getKwNew() {
    return findNotNullChildByType(KW_NEW);
  }

  @Override
  @Nullable
  public PsiElement getOpScolon() {
    return findChildByType(OP_SCOLON);
  }

}
