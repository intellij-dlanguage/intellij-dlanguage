// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import net.masterthought.dlanguage.psi.*;
import com.intellij.psi.stubs.IStubElementType;

public class DLanguageVarDeclarationsImpl extends DNamedStubbedPsiElementBase<?> implements DLanguageVarDeclarations {

  public DLanguageVarDeclarationsImpl(<T> stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageVarDeclarationsImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitVarDeclarations(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAutoDeclaration getAutoDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAutoDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageBasicType getBasicType() {
    return PsiTreeUtil.getChildOfType(this, DLanguageBasicType.class);
  }

  @Override
  @Nullable
  public DLanguageDeclarators getDeclarators() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDeclarators.class);
  }

  @Override
  @Nullable
  public DLanguageStorageClasses getStorageClasses() {
    return PsiTreeUtil.getChildOfType(this, DLanguageStorageClasses.class);
  }

  @Override
  @Nullable
  public PsiElement getOpScolon() {
    return findChildByType(OP_SCOLON);
  }

}
