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

public class DLanguageTemplateSingleArgumentImpl extends ASTWrapperPsiElement implements DLanguageTemplateSingleArgument {

  public DLanguageTemplateSingleArgumentImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitTemplateSingleArgument(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageBasicTypeX getBasicTypeX() {
    return PsiTreeUtil.getChildOfType(this, DLanguageBasicTypeX.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifierList getIdentifierList() {
    return PsiTreeUtil.getChildOfType(this, DLanguageIdentifierList.class);
  }

  @Override
  @Nullable
  public DLanguagePrimaryExpression getPrimaryExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguagePrimaryExpression.class);
  }

  @Override
  @Nullable
  public DLanguageTypeVector getTypeVector() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTypeVector.class);
  }

}
