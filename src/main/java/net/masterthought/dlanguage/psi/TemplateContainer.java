package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.utils.DUtil;

import java.util.List;

import static net.masterthought.dlanguage.psi.ContainerUtil.getDeclarations;

/**
 * Created by francis on 2/28/2017.
 * todo: using linked lists or passing res as a parameter will likely improve the performance of this
 */
public interface TemplateContainer extends Container {


    Class templateClass = DLanguageTemplateDeclaration.class;


    default <T extends DNamedElement> List<T> getTemplateDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return getDeclarations(this, templateClass, this.getClass(), includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

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
