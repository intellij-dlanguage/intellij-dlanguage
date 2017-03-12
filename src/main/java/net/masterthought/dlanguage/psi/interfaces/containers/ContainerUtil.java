package net.masterthought.dlanguage.psi.interfaces.containers;

import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.DLanguageAutoDeclarationY;
import net.masterthought.dlanguage.psi.DLanguageDeclaratorInitializer;
import net.masterthought.dlanguage.psi.interfaces.CanInherit;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;
import net.masterthought.dlanguage.psi.interfaces.Mixin;
import net.masterthought.dlanguage.psi.interfaces.Mixinable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static net.masterthought.dlanguage.utils.DUtil.getMixedInTemplates;

/**
 * Created by francis on 3/8/2017.
 *
 */
public class ContainerUtil {

    public static HashMap<Class<? extends DNamedElement>, Class<? extends Container>> declarationContainer = new HashMap<Class<? extends DNamedElement>, Class<? extends Container>>() {{
        put(AliasContainer.aliasClass, AliasContainer.class);
        put(ClassContainer.classClass, ClassContainer.class);
        put(ConstructorContainer.constructorClass, ConstructorContainer.class);
        put(DestructorContainer.destructorClass, DestructorContainer.class);
        put(EnumContainer.enumClass, EnumContainer.class);
        put(FunctionContainer.functionClass, FunctionContainer.class);
        put(GlobalVariableContainer.globalVarClass, GlobalVariableContainer.class);
        put(DLanguageAutoDeclarationY.class, GlobalVariableContainer.class);
        put(DLanguageDeclaratorInitializer.class, GlobalVariableContainer.class);
        put(InterfaceContainer.interfaceClass, InterfaceContainer.class);
        put(LocalVarContainer.variableDeclarationClass, LocalVarContainer.class);
        put(StructContainer.structClass, StructContainer.class);
        put(TemplateContainer.templateClass, TemplateContainer.class);
        put(TemplateMixinContainer.templateMixinClass, TemplateMixinContainer.class);
        put(UnionContainer.unionClass, UnionContainer.class);
        //remeber to update the below hashmap if you update this one
    }};
    public static HashMap<Class<? extends Container>, Class<? extends DNamedElement>> containerDeclaration = new HashMap<Class<? extends Container>, Class<? extends DNamedElement>>() {{
        put(AliasContainer.class, AliasContainer.aliasClass);
        put(ClassContainer.class, ClassContainer.classClass);
        put(ConstructorContainer.class, ConstructorContainer.constructorClass);
        put(DestructorContainer.class, DestructorContainer.destructorClass);
        put(EnumContainer.class, EnumContainer.enumClass);
        put(FunctionContainer.class, FunctionContainer.functionClass);
        put(GlobalVariableContainer.class, GlobalVariableContainer.globalVarClass);
        put(InterfaceContainer.class, InterfaceContainer.interfaceClass);
        put(LocalVarContainer.class, LocalVarContainer.variableDeclarationClass);
        put(StructContainer.class, StructContainer.structClass);
        put(TemplateContainer.class, TemplateContainer.templateClass);
        put(TemplateMixinContainer.class, TemplateMixinContainer.templateMixinClass);
        put(UnionContainer.class, UnionContainer.unionClass);
        //remember to update the above hashmap if you update this one
    }};

    /**
     * todo: using linked lists or passing res as a parameter will likely improve the performance of this
     * todo add alias this support
     *
     * @param <DeclarationClass>              the type of declaration we are looking for
     * @param elementToSearch                 the container we are looking for declarations in
     * @param topLevel                        the topLevel container
     * @param declarationClass                the Class object for the declaration we are looking for
     * @param includeFromMixins               whether to include declarations that are mixed into the container
     * @param includeFromInheritance          if the container in question can inherit, include or don't include from inheritance
     * @param includeNestedDeclarations       include nested declarations eg. whether to include foo in the example below:
     *                                        <code>
     *                                        void bar(){
     *                                        void foo(){
     *                                        <p>
     *                                        }
     *                                        }
     *                                        <code/>
     * @return a list containing all the declarations found.
     */
    public static <DeclarationClass extends DNamedElement> List<? extends DeclarationClass> getDeclarations(PsiElement elementToSearch, Container topLevel, Class<DeclarationClass> declarationClass, boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        List<DeclarationClass> res = new ArrayList<>();
        if (includeFromInheritance && declarationClass.isInstance(CanInherit.class)) {
            final List<CanInherit> whatInheritsFrom = ((CanInherit) elementToSearch).whatInheritsFrom();
            for (CanInherit canInherit : whatInheritsFrom) {
                res.addAll(getDeclarations(canInherit, topLevel, declarationClass, includeFromMixins, includeFromInheritance, includeNestedDeclarations));
            }
        }
        res.addAll(getDeclarationsImpl(elementToSearch, topLevel, declarationClass, includeFromMixins, includeNestedDeclarations, Visibility.private_));
        return res;
    }

    @NotNull
    public static <DeclarationClass extends DNamedElement> List<? extends DeclarationClass> getDeclarationsImpl(PsiElement elementToSearch, Container topLevel, Class<DeclarationClass> declarationClass, boolean includeFromMixins, boolean includeNestedDeclarations, Visibility visibility) {
        Class containerClass = declarationContainer.get(declarationClass);
        List<DeclarationClass> res = new ArrayList<>();
        if (declarationClass.isInstance(elementToSearch)) {
            if (!includeNestedDeclarations) {
                return Collections.singletonList((DeclarationClass) elementToSearch);
            }
            res.add((DeclarationClass) elementToSearch);
        }
        if (includeFromMixins && elementToSearch instanceof Mixin) {
            List<Mixinable> containersFromMixin = getMixedInTemplates((Mixin) elementToSearch);
            for (Mixinable templateContainer : containersFromMixin) {
                res.addAll(getDeclarations(templateContainer, topLevel, declarationClass, includeFromMixins, false, includeNestedDeclarations));//include from inheritance shouldn't matter for template mixins, however it could matter if mixing in a class. I'm unsure exactly how class mixins work todo
            }
        }
        if (!includeNestedDeclarations && containerClass.isInstance(elementToSearch) && elementToSearch != topLevel) {
            return res; // if not including nested declarations, stop recursive after a nested container has been found
        }

        for (PsiElement child : elementToSearch.getChildren()) {
            res.addAll(getDeclarationsImpl(topLevel, (Container) child, declarationClass, includeFromMixins, includeNestedDeclarations, visibility));
        }
        return res;
    }
}
