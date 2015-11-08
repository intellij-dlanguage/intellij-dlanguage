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

public class DLanguageBasicTypeImpl extends ASTWrapperPsiElement implements DLanguageBasicType {

  public DLanguageBasicTypeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitBasicType(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageBasicTypeX getBasicTypeX() {
    return findChildByClass(DLanguageBasicTypeX.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifierList getIdentifierList() {
    return findChildByClass(DLanguageIdentifierList.class);
  }

  @Override
  @Nullable
  public DLanguageType getType() {
    return findChildByClass(DLanguageType.class);
  }

  @Override
  @Nullable
  public DLanguageTypeVector getTypeVector() {
    return findChildByClass(DLanguageTypeVector.class);
  }

  @Override
  @Nullable
  public DLanguageTypeof getTypeof() {
    return findChildByClass(DLanguageTypeof.class);
  }

  @Override
  @Nullable
  public PsiElement getOpDot() {
    return findChildByType(OP_DOT);
  }

  @Override
  @Nullable
  public PsiElement getOpParLeft() {
    return findChildByType(OP_PAR_LEFT);
  }

  @Override
  @Nullable
  public PsiElement getOpParRight() {
    return findChildByType(OP_PAR_RIGHT);
  }

}
