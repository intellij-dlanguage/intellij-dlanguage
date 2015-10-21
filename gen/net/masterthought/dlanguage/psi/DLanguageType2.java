// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageType2 extends PsiElement {

  @Nullable
  DLanguageBuiltinType getBuiltinType();

  @Nullable
  DLanguageIdentifierOrTemplateChain getIdentifierOrTemplateChain();

  @Nullable
  DLanguageSymbol getSymbol();

  @Nullable
  DLanguageType getType();

  @Nullable
  DLanguageTypeConstructor getTypeConstructor();

  @Nullable
  DLanguageTypeofExpression getTypeofExpression();

  @Nullable
  DLanguageVector getVector();

  @Nullable
  PsiElement getOpDot();

  @Nullable
  PsiElement getOpParLeft();

  @Nullable
  PsiElement getOpParRight();

}
