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
import net.masterthought.dlanguage.stubs.DLanguageVarDeclaratorStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IElementType;

public class DLanguageDeclaratorInitializerImpl extends StubBasedPsiElementBase<DLanguageVarDeclaratorStub> implements DLanguageDeclaratorInitializer {

  public DLanguageDeclaratorInitializerImpl(DLanguageVarDeclaratorStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageDeclaratorInitializerImpl(ASTNode node) {
    super(node);
  }

  public DLanguageDeclaratorInitializerImpl(DLanguageVarDeclaratorStub stub, IElementType type, ASTNode node) {
    super(stub, type, node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitDeclaratorInitializer(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAltDeclarator getAltDeclarator() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAltDeclarator.class);
  }

  @Override
  @Nullable
  public DLanguageInitializer getInitializer() {
    return PsiTreeUtil.getChildOfType(this, DLanguageInitializer.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateParameters getTemplateParameters() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateParameters.class);
  }

  @Override
  @Nullable
  public DLanguageVarDeclarator getVarDeclarator() {
    return PsiTreeUtil.getChildOfType(this, DLanguageVarDeclarator.class);
  }

  @Override
  @Nullable
  public PsiElement getOpEq() {
    return findChildByType(OP_EQ);
  }

}
