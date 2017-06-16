package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.DLanguageTemplateAliasParameter;
import net.masterthought.dlanguage.psi.DLanguageTemplateParameter;
import net.masterthought.dlanguage.psi.DLanguageTemplateThisParameter;
import net.masterthought.dlanguage.psi.DLanguageTemplateTypeParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by francis on 3/4/2017.
 */
public interface HasTemplateParameters extends PsiElement {
//    default List<DLanguageTemplateParameter> getTemplateParametersList() {
//        final Collection<DLanguageTemplateParameter> childrenOfType = PsiTreeUtil.findChildrenOfType(this, DLanguageTemplateParameter.class);
//        return new ArrayList<>(childrenOfType);
//    }

    default List<DLanguageTemplateParameter> getTemplateParametersList() {
        return null;//todo
    }

    default List<DLanguageTemplateTypeParameter> getTypeParameters() {
        List<DLanguageTemplateTypeParameter> res = new ArrayList<>();
        for (DLanguageTemplateParameter parameter : getTemplateParametersList()) {
            if (parameter.getTemplateTypeParameter() != null) {
                res.add(parameter.getTemplateTypeParameter());
            }
        }
        return res;
    }

    default List<DLanguageTemplateAliasParameter> getTemplateAliasParameters() {
        List<DLanguageTemplateAliasParameter> res = new ArrayList<>();
        for (DLanguageTemplateParameter parameter : getTemplateParametersList()) {
            if (parameter.getTemplateAliasParameter() != null) {
                res.add(parameter.getTemplateAliasParameter());
            }
        }
        return res;
    }

    default List<DLanguageTemplateThisParameter> getTemplateThisParameters() {
        List<DLanguageTemplateThisParameter> res = new ArrayList<>();
        for (DLanguageTemplateParameter parameter : getTemplateParametersList()) {
            if (parameter.getTemplateThisParameter() != null) {
                res.add(parameter.getTemplateThisParameter());
            }
        }
        return res;
    }
}
