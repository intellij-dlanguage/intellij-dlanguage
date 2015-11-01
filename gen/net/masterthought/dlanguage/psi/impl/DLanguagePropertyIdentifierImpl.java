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

public class DLanguagePropertyIdentifierImpl extends ASTWrapperPsiElement implements DLanguagePropertyIdentifier {

  public DLanguagePropertyIdentifierImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitPropertyIdentifier(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getKwDisable() {
    return findChildByType(KW_DISABLE);
  }

  @Override
  @Nullable
  public PsiElement getKwNogc() {
    return findChildByType(KW_NOGC);
  }

  @Override
  @Nullable
  public PsiElement getKwProperty() {
    return findChildByType(KW_PROPERTY);
  }

  @Override
  @Nullable
  public PsiElement getKwSafe() {
    return findChildByType(KW_SAFE);
  }

  @Override
  @Nullable
  public PsiElement getKwSystem() {
    return findChildByType(KW_SYSTEM);
  }

  @Override
  @Nullable
  public PsiElement getKwTrusted() {
    return findChildByType(KW_TRUSTED);
  }

}
