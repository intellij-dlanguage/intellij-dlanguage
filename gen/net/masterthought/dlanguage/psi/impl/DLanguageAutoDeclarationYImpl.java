// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import com.intellij.extapi.psi.StubBasedPsiElementBase;
import net.masterthought.dlanguage.stubs.DLanguageAutoDeclarationStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IElementType;

public class DLanguageAutoDeclarationYImpl extends StubBasedPsiElementBase<DLanguageAutoDeclarationStub> implements DLanguageAutoDeclarationY {

  public DLanguageAutoDeclarationYImpl(DLanguageAutoDeclarationStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageAutoDeclarationYImpl(ASTNode node) {
    super(node);
  }

  public DLanguageAutoDeclarationYImpl(DLanguageAutoDeclarationStub stub, IElementType type, ASTNode node) {
    super(stub, type, node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitAutoDeclarationY(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageIdentifier getIdentifier() {
    return notNullChild(PsiTreeUtil.getStubChildOfType(this, DLanguageIdentifier.class));
  }

  @Override
  @NotNull
  public DLanguageInitializer getInitializer() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageInitializer.class));
  }

  @Override
  @Nullable
  public DLanguageTemplateParameters getTemplateParameters() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateParameters.class);
  }

  @Override
  @NotNull
  public PsiElement getOpEq() {
    return notNullChild(findChildByType(OP_EQ));
  }

}
