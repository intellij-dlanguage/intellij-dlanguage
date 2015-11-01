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

public class DLanguageStatementListNoCaseNoDefaultImpl extends ASTWrapperPsiElement implements DLanguageStatementListNoCaseNoDefault {

  public DLanguageStatementListNoCaseNoDefaultImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitStatementListNoCaseNoDefault(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageStatementListNoCaseNoDefault getStatementListNoCaseNoDefault() {
    return findChildByClass(DLanguageStatementListNoCaseNoDefault.class);
  }

  @Override
  @NotNull
  public DLanguageStatementNoCaseNoDefault getStatementNoCaseNoDefault() {
    return findNotNullChildByClass(DLanguageStatementNoCaseNoDefault.class);
  }

}
