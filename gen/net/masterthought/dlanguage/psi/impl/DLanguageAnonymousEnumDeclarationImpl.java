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
  public DLanguageAnonymousEnumMembers getAnonymousEnumMembers() {
    return findChildByClass(DLanguageAnonymousEnumMembers.class);
  }

  @Override
  @Nullable
  public DLanguageEnumMembers getEnumMembers() {
    return findChildByClass(DLanguageEnumMembers.class);
  }

  @Override
  @Nullable
  public DLanguageType getType() {
    return findChildByClass(DLanguageType.class);
  }

  @Override
  @NotNull
  public PsiElement getKwEnum() {
    return findNotNullChildByType(KW_ENUM);
  }

  @Override
  @Nullable
  public PsiElement getOpBracesLeft() {
    return findChildByType(OP_BRACES_LEFT);
  }

  @Override
  @Nullable
  public PsiElement getOpBracesRight() {
    return findChildByType(OP_BRACES_RIGHT);
  }

  @Override
  @Nullable
  public PsiElement getOpColon() {
    return findChildByType(OP_COLON);
  }

  @Override
  @Nullable
  public PsiElement getOpScolon() {
    return findChildByType(OP_SCOLON);
  }

}
