// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import net.masterthought.dlanguage.psi.*;

public class DLanguageAltDeclaratorXImpl extends ASTWrapperPsiElement implements DLanguageAltDeclaratorX {

  public DLanguageAltDeclaratorXImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAltDeclaratorX(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAltDeclarator getAltDeclarator() {
    return findChildByClass(DLanguageAltDeclarator.class);
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

}
