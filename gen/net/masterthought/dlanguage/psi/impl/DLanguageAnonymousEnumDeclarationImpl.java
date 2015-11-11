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

public class DLanguageAnonymousEnumDeclarationImpl extends ASTWrapperPsiElement implements DLanguageAnonymousEnumDeclaration {

  public DLanguageAnonymousEnumDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAnonymousEnumDeclaration(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageEnumBaseType getEnumBaseType() {
    return findChildByClass(DLanguageEnumBaseType.class);
  }

  @Override
  @NotNull
  public DLanguageEnumMembers getEnumMembers() {
    return findNotNullChildByClass(DLanguageEnumMembers.class);
  }

  @Override
  @NotNull
  public PsiElement getKwEnum() {
    return findNotNullChildByType(KW_ENUM);
  }

  @Override
  @NotNull
  public PsiElement getOpBracesLeft() {
    return findNotNullChildByType(OP_BRACES_LEFT);
  }

  @Override
  @NotNull
  public PsiElement getOpBracesRight() {
    return findNotNullChildByType(OP_BRACES_RIGHT);
  }

  @Override
  @Nullable
  public PsiElement getOpColon() {
    return findChildByType(OP_COLON);
  }

}
