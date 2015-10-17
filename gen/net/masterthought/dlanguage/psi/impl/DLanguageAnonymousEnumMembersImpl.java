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

public class DLanguageAnonymousEnumMembersImpl extends ASTWrapperPsiElement implements DLanguageAnonymousEnumMembers {

  public DLanguageAnonymousEnumMembersImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAnonymousEnumMembers(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<DLanguageAnonymousEnumMember> getAnonymousEnumMemberList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAnonymousEnumMember.class);
  }

  @Override
  @Nullable
  public DLanguageAnonymousEnumMembers getAnonymousEnumMembers() {
    return findChildByClass(DLanguageAnonymousEnumMembers.class);
  }

}
