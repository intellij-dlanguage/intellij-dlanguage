package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.masterthought.dlanguage.utils.DUtil.getMixedInTemplates;

/**
 * Created by francis on 3/8/2017.
 */
public class ContainerUtil {
    public static <ContainerType extends Container, DeclarationClass extends DNamedElement> List<? extends DeclarationClass> getDeclarations(Container container, Class<DeclarationClass> declarationClass, Class<ContainerType> containerClass, boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        List<DeclarationClass> res = new ArrayList<>();
        if (includeFromInheritance && declarationClass.isInstance(CanInherit.class)) {
            final List<CanInherit> whatInheritsFrom = ((CanInherit) container).whatInheritsFrom();
            for (CanInherit canInherit : whatInheritsFrom) {
                res.addAll(getDeclarations(canInherit, declarationClass, containerClass, includeFromMixins, includeFromInheritance, includeNestedDeclarations));
            }
        }
        res.addAll(getDeclarationsImpl(container, container, TemplateContainer.templateClass, container.getClass(), includeFromMixins, includeNestedDeclarations));
        return res;
    }

    @NotNull
    public static <ContainerType extends Container, DeclarationClass extends DNamedElement> List<? extends DeclarationClass> getDeclarationsImpl(ContainerType topLevelContainerClass, PsiElement elementToSearch, Class<? extends DeclarationClass> declarationClass, Class<? extends ContainerType> containerClass, boolean includeFromMixins, boolean includeNestedDeclarations) {
        List<DeclarationClass> res = new ArrayList<>();
        if (declarationClass.isInstance(elementToSearch)) {
            if (!includeNestedDeclarations) {
                return Collections.singletonList((DeclarationClass) elementToSearch);
            }
            res.add((DeclarationClass) elementToSearch);
        }
        if (includeFromMixins) {
            List<Mixinable> containersFromMixin = getMixedInTemplates(elementToSearch);
            for (Mixinable templateContainer : containersFromMixin) {
                res.addAll(getDeclarations(templateContainer, declarationClass, containerClass, true, false, includeNestedDeclarations));//include from inheritance shouldn't matter for templateClass mixins, however it could matter if mixing in a class. I'm unsure exactly how class mixins work todo
            }
        }
        if (!includeNestedDeclarations && containerClass.isInstance(elementToSearch) && elementToSearch != topLevelContainerClass) {
            return res; // if not including nested declarations, stop recursive after a nested container has been found
        }

        for (PsiElement child : elementToSearch.getChildren()) {
            res.addAll(getDeclarationsImpl(topLevelContainerClass, child, declarationClass, containerClass, includeFromMixins, includeNestedDeclarations));
        }
        return res;
    }
}
