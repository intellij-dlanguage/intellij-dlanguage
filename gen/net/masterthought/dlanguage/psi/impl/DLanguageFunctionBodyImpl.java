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

public class DLanguageFunctionBodyImpl extends ASTWrapperPsiElement implements DLanguageFunctionBody {

  public DLanguageFunctionBodyImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitFunctionBody(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageBlockStatement getBlockStatement() {
    return findChildByClass(DLanguageBlockStatement.class);
  }

  @Override
  @Nullable
  public DLanguageBodyStatement getBodyStatement() {
    return findChildByClass(DLanguageBodyStatement.class);
  }

  @Override
  @Nullable
  public DLanguageInStatement getInStatement() {
    return findChildByClass(DLanguageInStatement.class);
  }

  @Override
  @Nullable
  public DLanguageOutStatement getOutStatement() {
    return findChildByClass(DLanguageOutStatement.class);
  }

}
