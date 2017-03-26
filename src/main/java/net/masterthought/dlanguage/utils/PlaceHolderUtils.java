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
import static net.masterthought.dlanguage.utils.DResolveUtil.getVisibleElementsWithPlaceHolders;

/**
 * Created by francis on 3/17/2017.
 */
public class PlaceHolderUtils {

    static Set<DNamedElement> fillPlaceHolders(Set<DNamedElement> withPlaceHolders) {
        return fillPlaceHolders(withPlaceHolders, new HashSet<>());
    }

    private static Set<DNamedElement> fillPlaceHolders(Set<DNamedElement> withPlaceHolders, Set<PsiElement> excludedElements) {
        Set<DNamedElement> res = removeExcluded(withPlaceHolders, excludedElements);
        res = fillFilePlaceHolders(res, excludedElements);
        res = fillMembersPlaceHolder(res);
        res = fillMixinPlaceHolders(res);
        res = fillInheritancePlaceHolders(res, excludedElements);
        if (containsPlaceHolders(res)) {
            return fillPlaceHolders(res, excludedElements);
        }
        return res;
    }

    private static Set<DNamedElement> removeExcluded(Set<DNamedElement> withPlaceHolders, Set<PsiElement> excludedElements) {
        Set<DNamedElement> res = new HashSet<>();
        for (DNamedElement withPlaceHolder : withPlaceHolders) {
            if (withPlaceHolder instanceof PlaceHolder) {
                if (excludedElements.contains(((PlaceHolder) withPlaceHolder).getElement())) {
                    continue;
                }
            }
            res.add(withPlaceHolder);
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

    private static Set<DNamedElement> fillFilePlaceHolders(Set<DNamedElement> existingElements, Set<PsiElement> excludedElements) {
        Set<DNamedElement> res = new HashSet<>();
        for (DNamedElement declaration : existingElements) {
            if (declaration instanceof FilePlaceHolder) {
                if (!excludedElements.contains(((FilePlaceHolder) declaration).getFile())) {//technically this if is no longer necessary because removeExcluded should take care of this
                    res.addAll(getContainedDeclarationsWithPlaceHolders(((FilePlaceHolder) declaration).getFile()));
                    excludedElements.add(((FilePlaceHolder) declaration).getFile());
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
                    final Set<? extends DNamedElement> definitionNodes = DResolveUtil.findDefinitionNodes(findChildOfType(((MixinPlaceHolder) visibleElement).getMixin(), DLanguageIdentifier.class));
                    if (definitionNodes.size() != 1)
                        continue;
                    final PsiElement resolvedDef = (PsiElement) definitionNodes.toArray()[0];
                    res.addAll(getContainedDeclarationsWithPlaceHolders((Container) resolvedDef));
//                    res.add(visibleElement);
                }
            } else {
                res.add(visibleElement);
            }
        }
        return res;

    }

    private static Set<DNamedElement> fillInheritancePlaceHolders(Set<DNamedElement> withPlaceHolders, Set<PsiElement> excludedElements) {
        Set<DNamedElement> res = new HashSet<>();
        for (DNamedElement visibleElement : withPlaceHolders) {
            if (visibleElement instanceof InheritancePlaceHolder) {
                final InheritancePlaceHolder placeHolder = (InheritancePlaceHolder) visibleElement;
                Map<String, DLanguageIdentifier> whatInheritsFrom = placeHolder.getSuperClassNames();
                //to find the super class/ interface declaration, resolve the declaration.
                // cannot use getReference().resolve(), because excluded elements needs to be use
                for (DLanguageIdentifier identifier : whatInheritsFrom.values()) {
                    PsiElement resolve = null;
                    Set<DNamedElement> foundDeclarations = new HashSet<>();
                    for (DNamedElement canidateSuperDeclaration : fillPlaceHolders(getVisibleElementsWithPlaceHolders(identifier, identifier.getParentContainer()), new HashSet<PsiElement>(excludedElements) {{
                        add(((InheritancePlaceHolder) visibleElement).getElement());// this excluded element is required in the event of the declaration not existing. If it doesn't exist we don't want this method to be recursively called forever in an attempt to fill an unfillable inheritance placeholder
                    }})) {
                        if (canidateSuperDeclaration.getName().equals(identifier.getName())) {
                            foundDeclarations.add(canidateSuperDeclaration);
                        }
                    }
                    if (foundDeclarations.size() == 1) {
                        resolve = (PsiElement) foundDeclarations.toArray()[0];
                    }
                    if (resolve == null)
                        continue;//the super class/ implemented interface was not found, skip this resolution
                    res.addAll(getContainedDeclarationsWithPlaceHolders((Container) resolve));
                }
            } else {
                res.add(visibleElement);
            }
        }
        return res;

    }
}
