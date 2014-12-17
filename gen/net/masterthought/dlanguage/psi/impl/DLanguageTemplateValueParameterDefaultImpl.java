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

public class DLanguageTemplateValueParameterDefaultImpl extends ASTWrapperPsiElement implements DLanguageTemplateValueParameterDefault {

  public DLanguageTemplateValueParameterDefaultImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitTemplateValueParameterDefault(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAssignExpression getAssignExpression() {
    return findChildByClass(DLanguageAssignExpression.class);
  }

  @Override
  @NotNull
  public PsiElement getAssign() {
    return findNotNullChildByType(ASSIGN);
  }

  @Override
  @Nullable
  public PsiElement getSpecialFile() {
    return findChildByType(SPECIALFILE);
  }

  @Override
  @Nullable
  public PsiElement getSpecialFunction() {
    return findChildByType(SPECIALFUNCTION);
  }

  @Override
  @Nullable
  public PsiElement getSpecialLine() {
    return findChildByType(SPECIALLINE);
  }

  @Override
  @Nullable
  public PsiElement getSpecialModule() {
    return findChildByType(SPECIALMODULE);
  }

  @Override
  @Nullable
  public PsiElement getSpecialPrettyFunction() {
    return findChildByType(SPECIALPRETTYFUNCTION);
  }

}
