// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageVarDeclaratorStub;

public interface DLanguageDeclaratorInitializer extends DNamedElement, StubBasedPsiElement<DLanguageVarDeclaratorStub> {

  @Nullable
  DLanguageAltDeclarator getAltDeclarator();

  @Nullable
  DLanguageInitializer getInitializer();

  @Nullable
  DLanguageTemplateParameters getTemplateParameters();

  @Nullable
  DLanguageVarDeclarator getVarDeclarator();

  @Nullable
  PsiElement getOpEq();

}
