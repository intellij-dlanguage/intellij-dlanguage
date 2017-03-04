// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageUnionDeclarationStub;

public interface DLanguageUnionDeclaration extends StatementContainer, DNamedElement, HasVisibility, StubBasedPsiElement<DLanguageUnionDeclarationStub> {

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
