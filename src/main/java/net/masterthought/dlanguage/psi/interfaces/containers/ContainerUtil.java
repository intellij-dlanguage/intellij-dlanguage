package net.masterthought.dlanguage.psi.interfaces.containers;

import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.DLanguageAutoDeclarationY;
import net.masterthought.dlanguage.psi.DLanguageDeclaratorInitializer;
import net.masterthought.dlanguage.psi.interfaces.CanInherit;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.Mixin;
import net.masterthought.dlanguage.psi.interfaces.Mixinable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
        put(ImportContainer.importClass, ImportContainer.class);
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
        put(ImportContainer.class, ImportContainer.importClass);
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
        if (includeFromInheritance && CanInherit.class.isInstance(elementToSearch)) {
            final List<CanInherit> whatInheritsFrom = ((CanInherit) elementToSearch).whatInheritsFrom();
            for (CanInherit canInherit : whatInheritsFrom) {
                res.addAll(getDeclarations(canInherit, canInherit, declarationClass, includeFromMixins, includeFromInheritance, includeNestedDeclarations));//todo only get publics
            }
        }
        res.addAll(getDeclarationsImpl(elementToSearch, topLevel, declarationClass, includeFromMixins, includeNestedDeclarations));
        return res;
    }

    @NotNull
    public static <DeclarationClass extends DNamedElement> List<? extends DeclarationClass> getDeclarationsImpl(PsiElement elementToSearch, Container topLevel, Class<DeclarationClass> declarationClass, boolean includeFromMixins, boolean includeNestedDeclarations) {
        Class containerClass = declarationContainer.get(declarationClass);
        List<DeclarationClass> res = new ArrayList<>();
        if (declarationClass.isInstance(elementToSearch)) {
            if (!includeNestedDeclarations) {
                return Collections.singletonList((DeclarationClass) elementToSearch);
            }
            res.add((DeclarationClass) elementToSearch);
        }
        if (includeFromMixins && elementToSearch instanceof Mixin) {
            Mixinable whatWasMixedIn = ((Mixin) elementToSearch).getMixinableDeclaration();
            res.addAll(getDeclarations(whatWasMixedIn, whatWasMixedIn, declarationClass, includeFromMixins, false, includeNestedDeclarations));//include from inheritance shouldn't matter for template mixins, however it could matter if mixing in a class. I'm unsure exactly how class mixins work todo
        }
        if (!includeNestedDeclarations && containerClass.isInstance(elementToSearch) && elementToSearch != topLevel) {
            return res; // if not including nested declarations, stop recursing after a nested container has been found
        }

        for (PsiElement child : elementToSearch.getChildren()) {
            res.addAll(getDeclarationsImpl(child, topLevel, declarationClass, includeFromMixins, includeNestedDeclarations));
        }
        return res;
    }

    public static List<DNamedElement> getAllDeclarations(Container container, boolean includefromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        ArrayList<DNamedElement> res = new ArrayList<>();
        if (container instanceof UnionContainer) {
            res.addAll(((UnionContainer) container).getUnionDeclarations(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof ClassContainer) {
            res.addAll(((ClassContainer) container).getClassDeclarations(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof ConstructorContainer) {
            res.addAll(((ConstructorContainer) container).getConstructorDeclarations(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof DestructorContainer) {
            res.addAll(((DestructorContainer) container).getDestructorDeclarations(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof EnumContainer) {
            res.addAll(((EnumContainer) container).getEnumDeclarations(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof FunctionContainer) {
            res.addAll(((FunctionContainer) container).getFunctionDeclarations(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof GlobalVariableContainer) {
            res.addAll(((GlobalVariableContainer) container).getGlobalVariableDeclarations(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof InterfaceContainer) {
            res.addAll(((InterfaceContainer) container).getInterfaceDeclarations(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof LocalVarContainer) {
            res.addAll(((LocalVarContainer) container).getLocalVariableDeclarations(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof StructContainer) {
            res.addAll(((StructContainer) container).getStructDeclarations(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof TemplateContainer) {
            res.addAll(((TemplateContainer) container).getTemplateDeclarations(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof TemplateMixinContainer) {
            res.addAll(((TemplateMixinContainer) container).getTemplateMixinDeclarations(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        return res;
    }


    public static List<DNamedElement> getProtectedDeclarations(Container container, boolean includefromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        ArrayList<DNamedElement> res = new ArrayList<>();
        if (container instanceof UnionContainer) {
            res.addAll(((UnionContainer) container).getProtectedUnions(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof ClassContainer) {
            res.addAll(((ClassContainer) container).getProtectedClasses(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof ConstructorContainer) {
            res.addAll(((ConstructorContainer) container).getProtectedConstructors(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof DestructorContainer) {
            res.addAll(((DestructorContainer) container).getProtectedDestructors(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof EnumContainer) {
            res.addAll(((EnumContainer) container).getProtectedEnums(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof FunctionContainer) {
            res.addAll(((FunctionContainer) container).getProtectedFunctions(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof GlobalVariableContainer) {
            res.addAll(((GlobalVariableContainer) container).getProtectedGlobalVariables(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof InterfaceContainer) {
            res.addAll(((InterfaceContainer) container).getProtectedInterfaces(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof LocalVarContainer) {
            res.addAll(((LocalVarContainer) container).getLocalProtectedVariables(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof StructContainer) {
            res.addAll(((StructContainer) container).getProtectedStructs(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof TemplateContainer) {
            res.addAll(((TemplateContainer) container).getProtectedTemplates(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof TemplateMixinContainer) {
            res.addAll(((TemplateMixinContainer) container).getProtectedTemplateMixins(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        return res;
    }

    public static List<DNamedElement> getPublicDeclarations(Container container, boolean includefromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        ArrayList<DNamedElement> res = new ArrayList<>();
        if (container instanceof UnionContainer) {
            res.addAll(((UnionContainer) container).getPublicUnions(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof ClassContainer) {
            res.addAll(((ClassContainer) container).getPublicClasses(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof ConstructorContainer) {
            res.addAll(((ConstructorContainer) container).getPublicConstructors(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof DestructorContainer) {
            res.addAll(((DestructorContainer) container).getPublicDestructors(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof EnumContainer) {
            res.addAll(((EnumContainer) container).getPublicEnums(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof FunctionContainer) {
            res.addAll(((FunctionContainer) container).getPublicFunctions(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof GlobalVariableContainer) {
            res.addAll(((GlobalVariableContainer) container).getPublicGlobalVariables(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof InterfaceContainer) {
            res.addAll(((InterfaceContainer) container).getPublicInterfaces(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof LocalVarContainer) {
            res.addAll(((LocalVarContainer) container).getLocalPublicVariables(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof StructContainer) {
            res.addAll(((StructContainer) container).getPublicStructs(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof TemplateContainer) {
            res.addAll(((TemplateContainer) container).getPublicTemplates(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof TemplateMixinContainer) {
            res.addAll(((TemplateMixinContainer) container).getPublicTemplateMixins(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        return res;
    }

    public static List<DNamedElement> getPrivateDeclarations(Container container, boolean includefromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        ArrayList<DNamedElement> res = new ArrayList<>();
        if (container instanceof UnionContainer) {
            res.addAll(((UnionContainer) container).getPrivateUnions(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof ClassContainer) {
            res.addAll(((ClassContainer) container).getPrivateClasses(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof ConstructorContainer) {
            res.addAll(((ConstructorContainer) container).getPrivateConstructors(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof DestructorContainer) {
            res.addAll(((DestructorContainer) container).getPrivateDestructors(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof EnumContainer) {
            res.addAll(((EnumContainer) container).getPrivateEnums(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof FunctionContainer) {
            res.addAll(((FunctionContainer) container).getPrivateFunctions(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof GlobalVariableContainer) {
            res.addAll(((GlobalVariableContainer) container).getPrivateGlobalVariables(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof InterfaceContainer) {
            res.addAll(((InterfaceContainer) container).getPrivateInterfaces(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof LocalVarContainer) {
            res.addAll(((LocalVarContainer) container).getLocalPrivateVariables(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof StructContainer) {
            res.addAll(((StructContainer) container).getPrivateStructs(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof TemplateContainer) {
            res.addAll(((TemplateContainer) container).getPrivateTemplates(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        if (container instanceof TemplateMixinContainer) {
            res.addAll(((TemplateMixinContainer) container).getPrivateTemplateMixins(includefromMixins, includeFromInheritance, includeNestedDeclarations));
        }
        return res;
    }
}
