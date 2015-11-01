// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageCondition extends PsiElement {

  @Nullable
  DLanguageDebugCondition getDebugCondition();

  @Nullable
  DLanguageStaticIfCondition getStaticIfCondition();

  @Nullable
  DLanguageVersionCondition getVersionCondition();

}
