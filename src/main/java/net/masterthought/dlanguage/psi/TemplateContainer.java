package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.utils.DUtil;

import java.util.List;

/**
 * Created by francis on 2/28/2017.
 */
public interface TemplateContainer {
    List<DLanguageTemplateDeclaration> getTemplateDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations);

    default List<DLanguageTemplateDeclaration> getPublicTemplates(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getTemplateDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageTemplateDeclaration> getProtectedTemplates(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getTemplateDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageTemplateDeclaration> getPrivateTemplates(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getTemplateDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

}
