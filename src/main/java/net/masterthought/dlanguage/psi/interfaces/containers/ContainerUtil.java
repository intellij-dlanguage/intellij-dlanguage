package net.masterthought.dlanguage.psi.interfaces.containers;

import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.interfaces.CanInherit;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.Mixin;
import net.masterthought.dlanguage.psi.interfaces.VariableDeclaration;
import net.masterthought.dlanguage.psi.references.placeholders.*;
import net.masterthought.dlanguage.utils.DResolveUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by francis on 3/8/2017.
 *
 */
public class ContainerUtil {

    /**
     * finds declarations in the current container. This will not recurse into nested definition, but will return placeholders for class method.
     * @param container
     * @return
     */
    public static Set<DNamedElement> getContainedDeclarationsWithPlaceHolders(Container container) {
        Set<DNamedElement> res = new HashSet<>();
        getContainedDeclarationsWithPlaceHoldersImpl(container, res);
        return res;
    }

    public static void getContainedDeclarationsWithPlaceHoldersImpl(@NotNull PsiElement element, Set<DNamedElement> res) {
        //add placeHolders for stuff that has members
        if (element instanceof DLanguageClassDeclaration) {
            res.add(new ClassMembersPlaceHolder((DLanguageClassDeclaration) element));
        }
        if (element instanceof DLanguageStructDeclaration) {
            res.add(new StructMembersPlaceHolder((DLanguageStructDeclaration) element));
        }
        if (element instanceof DLanguageInterfaceDeclaration) {
            res.add(new InterfaceMembersPlaceHolder((DLanguageInterfaceDeclaration) element));
        }
        if (element instanceof DLanguageTemplateDeclaration) {
            res.add(new TemplateMembersPlaceHolder((DLanguageTemplateDeclaration) element));
        }
        if (element instanceof DLanguageTemplateMixinDeclaration) {
            res.add(new TemplateMixinMembersPlaceHolder((DLanguageTemplateMixinDeclaration) element));
        }
        if (element instanceof DLanguageImport) {
            final Set<DNamedElement> moduleDefinitionNodes = DResolveUtil.findModuleDefinitionNodes(((DLanguageImport) element).getModuleFullyQualifiedName());
            for (DNamedElement moduleDefinitionNode : moduleDefinitionNodes) {
                if (moduleDefinitionNode instanceof DLanguageFile) {
                    res.add(new FilePlaceHolder((DLanguageFile) moduleDefinitionNode));
                }
                if (moduleDefinitionNode instanceof DLanguageModuleDeclaration) {
                    res.add(new FilePlaceHolder((DLanguageFile) moduleDefinitionNode.getContainingFile()));
                }
            }
        }
        //add placeHolders for mixins
        if (element instanceof Mixin) {
            res.add(new MixinPlaceHolder((Mixin) element));
        }
        //add placeHolders for inheritance
        if (element instanceof CanInherit) {
            res.add(new InheritancePlaceHolder((CanInherit) element));
        }
        if (element instanceof VariableDeclaration) {
            if (((VariableDeclaration) element).actuallyIsDeclaration())
                res.add((VariableDeclaration) element);
        }
        //get remaining declarations
        if (element instanceof DLanguageFuncDeclaration ||
            element instanceof DLanguageDeclaratorInitializer ||
            element instanceof DLanguageAutoDeclarationY ||
            element instanceof DLanguageUnionDeclaration ||
            element instanceof DLanguageEnumDeclaration ||
            element instanceof DLanguageClassDeclaration ||
            element instanceof DLanguageStructDeclaration ||
            element instanceof DLanguageInterfaceDeclaration ||
            element instanceof DLanguageTemplateDeclaration ||
            element instanceof DLanguageTemplateMixinDeclaration ||
            element instanceof DLanguageAliasDeclaration ||
            element instanceof DLanguageConstructor ||
            element instanceof DLanguageLabeledStatement) {
            res.add((DNamedElement) element);
        }

        for (PsiElement child : element.getChildren()) {
            getContainedDeclarationsWithPlaceHoldersImpl(child, res);
        }

    }

}
