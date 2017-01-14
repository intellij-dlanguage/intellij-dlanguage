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

public class DLanguageQualifiedIdentifierListImpl extends ASTWrapperPsiElement implements DLanguageQualifiedIdentifierList {

  public DLanguageQualifiedIdentifierListImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitQualifiedIdentifierList(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageQualifiedIdentifierList getQualifiedIdentifierList() {
    return PsiTreeUtil.getChildOfType(this, DLanguageQualifiedIdentifierList.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateInstance getTemplateInstance() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateInstance.class);
  }

  @Override
  @Nullable
  public PsiElement getOpDot() {
    return findChildByType(OP_DOT);
  }

}
