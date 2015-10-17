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

public class DLanguageDeclarationImpl extends ASTWrapperPsiElement implements DLanguageDeclaration {

  public DLanguageDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitDeclaration(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<DLanguageAttribute> getAttributeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAttribute.class);
  }

  @Override
  @NotNull
  public DLanguageDeclaration2 getDeclaration2() {
    return findNotNullChildByClass(DLanguageDeclaration2.class);
  }

}
