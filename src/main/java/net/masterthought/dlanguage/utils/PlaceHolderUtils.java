package net.masterthought.dlanguage.utils;

import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.Mixin;
import net.masterthought.dlanguage.psi.interfaces.containers.Container;
import net.masterthought.dlanguage.psi.interfaces.containers.FilePlaceHolder;
import net.masterthought.dlanguage.psi.references.placeholders.*;
import org.jetbrains.annotations.Contract;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.intellij.psi.util.PsiTreeUtil.findChildOfType;
import static net.masterthought.dlanguage.psi.interfaces.containers.ContainerUtil.getContainedDeclarationsWithPlaceHolders;
import static net.masterthought.dlanguage.psi.interfaces.containers.ContainerUtil.getContainedDeclarationsWithPlaceHoldersImpl;

/**
 * Created by francis on 3/17/2017.
 */
public class PlaceHolderUtils {

    static Set<DNamedElement> fillPlaceHolders(Set<DNamedElement> withPlaceHolders) {
        return fillPlaceHolders(withPlaceHolders, new HashSet<>());
    }

    private static Set<DNamedElement> fillPlaceHolders(Set<DNamedElement> withPlaceHolders, Set<DLanguageFile> excludedFiles) {
        Set<DNamedElement> res = fillFilePlaceHolders(withPlaceHolders, excludedFiles);
        res = fillMembersPlaceHolder(res);
        res = fillMixinPlaceHolders(res);
        res = fillInheritancePlaceHolders(res);
        if (containsPlaceHolders(res)) {
            return fillPlaceHolders(res, excludedFiles);
        }
        return res;
    }

    private static Set<DNamedElement> fillMembersPlaceHolder(Set<DNamedElement> existingElements) {
        Set<DNamedElement> res = new HashSet<>();
        for (DNamedElement declaration : existingElements) {
            if (declaration instanceof StructMembersPlaceHolder) {
                final DLanguageStructDeclaration structDeclaration = ((StructMembersPlaceHolder) declaration).getElement();
                if (structDeclaration.getAggregateBody() == null)
                    continue;//non recoverable issue with psi
                getContainedDeclarationsWithPlaceHoldersImpl(structDeclaration.getAggregateBody(), res);
            } else if (declaration instanceof ClassMembersPlaceHolder) {
                final DLanguageClassDeclaration classDeclaration = ((ClassMembersPlaceHolder) declaration).getElement();
                if (classDeclaration.getAggregateBody() == null)
                    continue;
                getContainedDeclarationsWithPlaceHoldersImpl(classDeclaration.getAggregateBody(), res);
            } else if (declaration instanceof TemplateMembersPlaceHolder) {
                final DLanguageTemplateDeclaration templateDeclaration = ((TemplateMembersPlaceHolder) declaration).getElement();
                if (templateDeclaration.getDeclDefs() == null)
                    continue;
                getContainedDeclarationsWithPlaceHoldersImpl(templateDeclaration.getDeclDefs(), res);
            } else if (declaration instanceof InterfaceMembersPlaceHolder) {
                final DLanguageInterfaceDeclaration interfaceDeclaration = ((InterfaceMembersPlaceHolder) declaration).getElement();
                if (interfaceDeclaration.getAggregateBody() == null)
                    continue;
                getContainedDeclarationsWithPlaceHoldersImpl(interfaceDeclaration.getAggregateBody(), res);
            } else {
                res.add(declaration);
            }
        }
        return res;
    }

    private static Set<DNamedElement> fillFilePlaceHolders(Set<DNamedElement> existingElements, Set<DLanguageFile> excludedFiles) {
        Set<DNamedElement> res = new HashSet<>();
        for (DNamedElement declaration : existingElements) {
            if (declaration instanceof FilePlaceHolder) {
                if (!excludedFiles.contains(((FilePlaceHolder) declaration).getFile())) {
                    res.addAll(getContainedDeclarationsWithPlaceHolders(((FilePlaceHolder) declaration).getFile()));
                    excludedFiles.add(((FilePlaceHolder) declaration).getFile());
                    if (findChildOfType(((FilePlaceHolder) declaration).getFile(), DLanguageModuleDeclaration.class) != null)
                        res.add(findChildOfType(((FilePlaceHolder) declaration).getFile(), DLanguageModuleDeclaration.class));
                    else
                        res.add(((FilePlaceHolder) declaration).getFile());
                }
            } else {
                res.add(declaration);
            }
        }
        return res;
    }

    @Contract(pure = true)
    private static boolean containsPlaceHolders(Set<DNamedElement> list) {
        for (DNamedElement dNamedElement : list) {
            if (dNamedElement instanceof PlaceHolder)
                return true;
        }
        return false;
    }

    private static Set<DNamedElement> fillMixinPlaceHolders(Set<DNamedElement> withPlaceHolders) {
        Set<DNamedElement> res = new HashSet<>();
        for (DNamedElement visibleElement : withPlaceHolders) {
            if (visibleElement instanceof MixinPlaceHolder) {
                final Mixin mixin = ((MixinPlaceHolder) visibleElement).getMixin();
                final String name = mixin.getName();
                //search for definition
                boolean success = false;
                for (DNamedElement element : withPlaceHolders) {
                    if ((!(element instanceof PlaceHolder)) && element.getName().equals(name)) {
                        res.addAll(getContainedDeclarationsWithPlaceHolders((Container) element));
                        success = true;
                        break;
                    }
                }
                if (!success) {
//                    Set<DLanguageImport> imports = new HashSet<>();
//                    getVisibleImports(visibleElement,imports);
//                    Set<String> names = new HashSet<>();
//                    for (DLanguageImport import_ : imports) {
//                        names.add(import_.getName());
//                    }
//                    final Set<DLanguageFile> importedFiles = DResolveUtil.fromModulesToFiles(visibleElement.getProject(), names);
//                    for (DLanguageFile importedFile : importedFiles) {
//                        res.add()
//                    }
                    final PsiElement resolvedDef = findChildOfType(((MixinPlaceHolder) visibleElement).getMixin(), DLanguageIdentifier.class).getReference().resolve();
                    res.addAll(getContainedDeclarationsWithPlaceHolders((Container) resolvedDef));
//                    res.add(visibleElement);
                }
            } else {
                res.add(visibleElement);
            }
        }
        return res;

    }

    private static Set<DNamedElement> fillInheritancePlaceHolders(Set<DNamedElement> withPlaceHolders) {
        Set<DNamedElement> res = new HashSet<>();
        for (DNamedElement visibleElement : withPlaceHolders) {
            if (visibleElement instanceof InheritancePlaceHolder) {
                final InheritancePlaceHolder placeHolder = (InheritancePlaceHolder) visibleElement;
                Map<String, DLanguageIdentifier> whatInheritsFrom = placeHolder.getSuperClassNames();
                //search for declarations which resolve the class declaration. This could foreseeable cause bugs in the distant future, if there where two classes/interfaces with the same name are both visible from here todo
                for (Object object : withPlaceHolders.toArray()) { //avoid concurrent modification exceptions
                    DNamedElement element = (DNamedElement) object;
                    if (!(element instanceof PlaceHolder) && whatInheritsFrom.keySet().contains(element.getName())) {
                        // yes I know deeply nested for loops, could cause performance issues.
                        res.addAll(getContainedDeclarationsWithPlaceHolders((Container) element));
                        whatInheritsFrom.remove(element.getName());
                    }
                }
                //if  unable to find the super class/ interface declaration in current scope, resolve the declaration.
                for (DLanguageIdentifier identifier : whatInheritsFrom.values()) {
                    final PsiElement resolve = identifier.getReference().resolve();
                    res.addAll(getContainedDeclarationsWithPlaceHolders((Container) resolve));
                }
            } else {
                res.add(visibleElement);
            }
        }
        return res;

    }
}
