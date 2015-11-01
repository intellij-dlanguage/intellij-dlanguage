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

public class DLanguageBasicType2Impl extends ASTWrapperPsiElement implements DLanguageBasicType2 {

  public DLanguageBasicType2Impl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitBasicType2(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageBasicType2 getBasicType2() {
    return findChildByClass(DLanguageBasicType2.class);
  }

  @Override
  @NotNull
  public DLanguageBasicType2X getBasicType2X() {
    return findNotNullChildByClass(DLanguageBasicType2X.class);
  }

}
