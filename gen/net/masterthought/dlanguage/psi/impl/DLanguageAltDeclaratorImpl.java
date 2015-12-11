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

public class DLanguageAltDeclaratorImpl extends ASTWrapperPsiElement implements DLanguageAltDeclarator {

  public DLanguageAltDeclaratorImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAltDeclarator(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAltDeclaratorSuffixes getAltDeclaratorSuffixes() {
    return findChildByClass(DLanguageAltDeclaratorSuffixes.class);
  }

  @Override
  @Nullable
  public DLanguageAltDeclaratorX getAltDeclaratorX() {
    return findChildByClass(DLanguageAltDeclaratorX.class);
  }

  @Override
  @Nullable
  public DLanguageAltFuncDeclaratorSuffix getAltFuncDeclaratorSuffix() {
    return findChildByClass(DLanguageAltFuncDeclaratorSuffix.class);
  }

  @Override
  @Nullable
  public DLanguageBasicType2 getBasicType2() {
    return findChildByClass(DLanguageBasicType2.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return findChildByClass(DLanguageIdentifier.class);
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
