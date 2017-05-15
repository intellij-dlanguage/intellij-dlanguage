// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageNewAnonClassExpression extends PsiElement {

  @Nullable
  DLanguageAggregateBody getAggregateBody();

  @Nullable
  DLanguageAllocatorArguments getAllocatorArguments();

  @Nullable
  DLanguageClassArguments getClassArguments();

  @Nullable
  DLanguageInterfaces getInterfaces();

  @Nullable
  DLanguageSuperClass getSuperClass();

  @Nullable
  PsiElement getKwClass();

  @NotNull
  PsiElement getKwNew();

}
