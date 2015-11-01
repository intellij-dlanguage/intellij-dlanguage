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

public class DLanguageVarDeclarationsImpl extends ASTWrapperPsiElement implements DLanguageVarDeclarations {

  public DLanguageVarDeclarationsImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitVarDeclarations(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAutoDeclaration getAutoDeclaration() {
    return findChildByClass(DLanguageAutoDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageBasicType getBasicType() {
    return findChildByClass(DLanguageBasicType.class);
  }

  @Override
  @Nullable
  public DLanguageDeclarators getDeclarators() {
    return findChildByClass(DLanguageDeclarators.class);
  }

  @Override
  @Nullable
  public DLanguageStorageClasses getStorageClasses() {
    return findChildByClass(DLanguageStorageClasses.class);
  }

  @Override
  @Nullable
  public PsiElement getOpScolon() {
    return findChildByType(OP_SCOLON);
  }

}
