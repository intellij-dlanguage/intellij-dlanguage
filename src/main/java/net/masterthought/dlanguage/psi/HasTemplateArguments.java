package net.masterthought.dlanguage.psi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by francis on 3/4/2017.
 */
public interface HasTemplateArguments {
    List<DLanguageTemplateParameter> getTemplateParametersList();

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
