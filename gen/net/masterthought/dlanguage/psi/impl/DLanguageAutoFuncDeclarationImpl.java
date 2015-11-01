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

public class DLanguageAutoFuncDeclarationImpl extends ASTWrapperPsiElement implements DLanguageAutoFuncDeclaration {

  public DLanguageAutoFuncDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAutoFuncDeclaration(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageFuncDeclaratorSuffix getFuncDeclaratorSuffix() {
    return findNotNullChildByClass(DLanguageFuncDeclaratorSuffix.class);
  }

  @Override
  @NotNull
  public DLanguageFunctionBody getFunctionBody() {
    return findNotNullChildByClass(DLanguageFunctionBody.class);
  }

  @Override
  @NotNull
  public DLanguageIdentifier getIdentifier() {
    return findNotNullChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @NotNull
  public DLanguageStorageClasses getStorageClasses() {
    return findNotNullChildByClass(DLanguageStorageClasses.class);
  }

}
