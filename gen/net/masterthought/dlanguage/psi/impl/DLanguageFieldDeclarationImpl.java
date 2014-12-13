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

public class DLanguageFieldDeclarationImpl extends ASTWrapperPsiElement implements DLanguageFieldDeclaration {

  public DLanguageFieldDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitFieldDeclaration(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageConstructorDeclaration getConstructorDeclaration() {
    return findChildByClass(DLanguageConstructorDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageDocComment getDocComment() {
    return findChildByClass(DLanguageDocComment.class);
  }

  @Override
  @Nullable
  public DLanguageMethodDeclaration getMethodDeclaration() {
    return findChildByClass(DLanguageMethodDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageStaticInitializer getStaticInitializer() {
    return findChildByClass(DLanguageStaticInitializer.class);
  }

  @Override
  @Nullable
  public DLanguageVariableDeclaration getVariableDeclaration() {
    return findChildByClass(DLanguageVariableDeclaration.class);
  }

}
