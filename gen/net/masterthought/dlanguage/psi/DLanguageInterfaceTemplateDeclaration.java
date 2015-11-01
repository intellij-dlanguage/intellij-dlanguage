// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageInterfaceTemplateDeclaration extends PsiElement {

  @NotNull
  List<DLanguageAggregateBody> getAggregateBodyList();

  @NotNull
  List<DLanguageBaseInterfaceList> getBaseInterfaceListList();

  @NotNull
  List<DLanguageConstraint> getConstraintList();

  @NotNull
  List<DLanguageIdentifier> getIdentifierList();

  @NotNull
  List<DLanguageTemplateParameters> getTemplateParametersList();

}
