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

public class DLanguageClassDeclarationImpl extends ASTWrapperPsiElement implements DLanguageClassDeclaration {

  public DLanguageClassDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitClassDeclaration(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageClassName getClassName() {
    return findChildByClass(DLanguageClassName.class);
  }

  @Override
  @NotNull
  public DLanguageFieldDeclaration getFieldDeclaration() {
    return findNotNullChildByClass(DLanguageFieldDeclaration.class);
  }

  @Override
  @NotNull
  public DLanguageIdentifier getIdentifier() {
    return findNotNullChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @NotNull
  public List<DLanguageInterfaceName> getInterfaceNameList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageInterfaceName.class);
  }

  @Override
  @NotNull
  public DLanguageModifier getModifier() {
    return findNotNullChildByClass(DLanguageModifier.class);
  }

}
