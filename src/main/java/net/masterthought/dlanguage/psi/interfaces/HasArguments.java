package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.DLanguageParameter;

import java.util.List;

/**
 * Created by francis on 3/4/2017.
 * Use HasTemplateArguments for accessing template arguments
 */
public interface HasArguments extends PsiElement {
    List<DLanguageParameter> getArguments();
}
