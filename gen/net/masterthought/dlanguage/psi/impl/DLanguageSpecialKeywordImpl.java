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

public class DLanguageSpecialKeywordImpl extends ASTWrapperPsiElement implements DLanguageSpecialKeyword {

  public DLanguageSpecialKeywordImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitSpecialKeyword(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getKwFile() {
    return findChildByType(KW___FILE__);
  }

  @Override
  @Nullable
  public PsiElement getKwFunction() {
    return findChildByType(KW___FUNCTION__);
  }

  @Override
  @Nullable
  public PsiElement getKwLine() {
    return findChildByType(KW___LINE__);
  }

  @Override
  @Nullable
  public PsiElement getKwModule() {
    return findChildByType(KW___MODULE__);
  }

  @Override
  @Nullable
  public PsiElement getKwPrettyFunction() {
    return findChildByType(KW___PRETTY_FUNCTION__);
  }

}
