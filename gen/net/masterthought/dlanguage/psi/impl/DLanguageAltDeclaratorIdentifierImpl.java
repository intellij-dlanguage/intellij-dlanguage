// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;

import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import net.masterthought.dlanguage.psi.*;

public class DLanguageAltDeclaratorIdentifierImpl extends ASTWrapperPsiElement implements DLanguageAltDeclaratorIdentifier {

  public DLanguageAltDeclaratorIdentifierImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAltDeclaratorIdentifier(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAltDeclaratorSuffixes getAltDeclaratorSuffixes() {
    return findChildByClass(DLanguageAltDeclaratorSuffixes.class);
  }

  @Override
  @Nullable
  public DLanguageBasicType2 getBasicType2() {
    return findChildByClass(DLanguageBasicType2.class);
  }

  @Override
  @NotNull
  public DLanguageIdentifier getIdentifier() {
    return findNotNullChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageInitializer getInitializer() {
    return findChildByClass(DLanguageInitializer.class);
  }

  @Override
  @Nullable
  public PsiElement getOpEq() {
    return findChildByType(OP_EQ);
  }

}
