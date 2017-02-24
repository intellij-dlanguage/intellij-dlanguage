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

public class DLanguageLabeledStatementImpl extends DNamedStubbedPsiElementBase<?> implements DLanguageLabeledStatement {

  public DLanguageLabeledStatementImpl(<T> stub, IStubElementType type) {
    super(stub, type);
  }

  public DLanguageLabeledStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitLabeledStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageIdentifier getIdentifier() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class));
  }

  @Override
  @Nullable
  public DLanguageStatement getStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageStatement.class);
  }

  @Override
  @NotNull
  public PsiElement getOpColon() {
    return notNullChild(findChildByType(OP_COLON));
  }

}
