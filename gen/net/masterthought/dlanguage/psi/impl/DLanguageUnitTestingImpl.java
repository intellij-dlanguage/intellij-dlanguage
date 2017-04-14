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
import net.masterthought.dlanguage.stubs.interfaces.UnitTestingStub;
import net.masterthought.dlanguage.psi.*;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IElementType;

public class DLanguageUnitTestingImpl extends DStubbedPsiElementBase<UnitTestingStub> implements DLanguageUnitTesting {

  public DLanguageUnitTestingImpl(UnitTestingStub stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageUnitTestingImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitUnitTesting(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageBlockStatement getBlockStatement() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageBlockStatement.class));
  }

  @Override
  @NotNull
  public PsiElement getKwUnittest() {
    return notNullChild(findChildByType(KW_UNITTEST));
  }

}
