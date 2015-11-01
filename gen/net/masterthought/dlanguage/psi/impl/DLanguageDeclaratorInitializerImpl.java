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

public class DLanguageDeclaratorInitializerImpl extends ASTWrapperPsiElement implements DLanguageDeclaratorInitializer {

  public DLanguageDeclaratorInitializerImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitDeclaratorInitializer(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAltDeclarator getAltDeclarator() {
    return findChildByClass(DLanguageAltDeclarator.class);
  }

  @Override
  @Nullable
  public DLanguageInitializer getInitializer() {
    return findChildByClass(DLanguageInitializer.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateParameters getTemplateParameters() {
    return findChildByClass(DLanguageTemplateParameters.class);
  }

  @Override
  @Nullable
  public DLanguageVarDeclarator getVarDeclarator() {
    return findChildByClass(DLanguageVarDeclarator.class);
  }

  @Override
  @Nullable
  public PsiElement getOpEq() {
    return findChildByType(OP_EQ);
  }

}
