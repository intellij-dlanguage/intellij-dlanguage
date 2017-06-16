package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.DLanguageParameter;

import java.util.List;

/**
 * Created by francis on 3/4/2017.
 * Use HasTemplateParameters for accessing template arguments
 */
public interface HasParameters extends PsiElement {
    default List<DLanguageParameter> getParameterList() {
        return null;//todo
    }
}
