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

public class DLanguageClassTemplateDeclarationImpl extends ASTWrapperPsiElement implements DLanguageClassTemplateDeclaration {

  public DLanguageClassTemplateDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitClassTemplateDeclaration(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageAggregateBody getAggregateBody() {
    return findNotNullChildByClass(DLanguageAggregateBody.class);
  }

  @Override
  @Nullable
  public DLanguageBaseClassList getBaseClassList() {
    return findChildByClass(DLanguageBaseClassList.class);
  }

  @Override
  @Nullable
  public DLanguageConstraint getConstraint() {
    return findChildByClass(DLanguageConstraint.class);
  }

  @Override
  @NotNull
  public DLanguageIdentifier getIdentifier() {
    return findNotNullChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @NotNull
  public DLanguageTemplateParameters getTemplateParameters() {
    return findNotNullChildByClass(DLanguageTemplateParameters.class);
  }

  @Override
  @NotNull
  public PsiElement getKwClass() {
    return findNotNullChildByType(KW_CLASS);
  }

}
