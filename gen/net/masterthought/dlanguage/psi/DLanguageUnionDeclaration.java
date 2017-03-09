// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.*;
import net.masterthought.dlanguage.psi.interfaces.containers.StatementContainer;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageUnionDeclarationStub;

public interface DLanguageUnionDeclaration extends StatementContainer, DNamedElement, HasVisibility, HasTemplateArguments, HasArguments, StubBasedPsiElement<DLanguageUnionDeclarationStub> {

  @Nullable
  DLanguageAggregateBody getAggregateBody();

  @Nullable
  DLanguageAnonUnionDeclaration getAnonUnionDeclaration();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageUnionTemplateDeclaration getUnionTemplateDeclaration();

  @Nullable
  PsiElement getKwUnion();

  @Nullable
  PsiElement getOpScolon();

}
