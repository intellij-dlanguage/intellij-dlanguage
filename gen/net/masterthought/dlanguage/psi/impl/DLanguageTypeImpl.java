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

public class DLanguageTypeImpl extends ASTWrapperPsiElement implements DLanguageType {

  public DLanguageTypeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitType(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageType2 getType2() {
    return findNotNullChildByClass(DLanguageType2.class);
  }

  @Override
  @Nullable
  public DLanguageTypeConstructors getTypeConstructors() {
    return findChildByClass(DLanguageTypeConstructors.class);
  }

  @Override
  @NotNull
  public List<DLanguageTypeSuffix> getTypeSuffixList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageTypeSuffix.class);
  }

}
