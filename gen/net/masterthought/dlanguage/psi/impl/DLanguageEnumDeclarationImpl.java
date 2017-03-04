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
import net.masterthought.dlanguage.stubs.DLanguageUnionDeclarationStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IElementType;

public class DLanguageEnumDeclarationImpl extends StubBasedPsiElementBase<DLanguageUnionDeclarationStub> implements DLanguageEnumDeclaration {

  public DLanguageEnumDeclarationImpl(DLanguageUnionDeclarationStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageEnumDeclarationImpl(ASTNode node) {
    super(node);
  }

  public DLanguageEnumDeclarationImpl(DLanguageUnionDeclarationStub stub, IElementType type, ASTNode node) {
    super(stub, type, node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitEnumDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAnonymousEnumDeclaration getAnonymousEnumDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAnonymousEnumDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageEnumBaseType getEnumBaseType() {
    return PsiTreeUtil.getChildOfType(this, DLanguageEnumBaseType.class);
  }

  @Override
  @Nullable
  public DLanguageEnumBody getEnumBody() {
    return PsiTreeUtil.getChildOfType(this, DLanguageEnumBody.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return PsiTreeUtil.getStubChildOfType(this, DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public PsiElement getKwEnum() {
    return findChildByType(KW_ENUM);
  }

  @Override
  @Nullable
  public PsiElement getOpColon() {
    return findChildByType(OP_COLON);
  }

}
