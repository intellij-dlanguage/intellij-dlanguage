package net.masterthought.dlanguage.utils;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageIdentifier;
import net.masterthought.dlanguage.psi.DLanguageModuleDeclaration;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.Mixin;
import net.masterthought.dlanguage.psi.interfaces.containers.*;
import org.jetbrains.annotations.Contract;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static net.masterthought.dlanguage.psi.interfaces.containers.ContainerUtil.getAllDeclarationsWithPlaceHolders;

/**
 * Created by francis on 3/17/2017.
 */
public class PlaceHolderUtils {
    static Set<DNamedElement> fillPlaceHolders(Set<DNamedElement> withPlaceHolders) {
        Set<DNamedElement> res = fillFilePlaceHolders(withPlaceHolders);
        res = fillMixinPlaceHolders(res);
        res = fillInheritancePlaceHolders(res);
        if (containsPlaceHolders(res)) {
            return fillPlaceHolders(res);
        }
        return res;
    }

    private static Set<DNamedElement> fillFilePlaceHolders(Set<DNamedElement> existingElements) {
        Set<DNamedElement> res = new HashSet<>();
        for (DNamedElement declaration : existingElements) {
            if (declaration instanceof FilePlaceHolder) {
                res.addAll(getAllDeclarationsWithPlaceHolders(((FilePlaceHolder) declaration).getFile()));
                if (PsiTreeUtil.findChildOfType(((FilePlaceHolder) declaration).getFile(), DLanguageModuleDeclaration.class) != null)
                    res.add(PsiTreeUtil.findChildOfType(((FilePlaceHolder) declaration).getFile(), DLanguageModuleDeclaration.class));
                else
                    res.add(((FilePlaceHolder) declaration).getFile());
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
                for (DNamedElement element : withPlaceHolders) {
                    if ((!(element instanceof PlaceHolder)) && element.getName().equals(name)) {
                        res.addAll(getAllDeclarationsWithPlaceHolders((Container) element));
                    }
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
                for (DNamedElement element : withPlaceHolders) {
                    if (!(element instanceof PlaceHolder) && whatInheritsFrom.keySet().contains(element.getName())) {
                        // yes I know deeply nested for loops, that could cause performance issues.
                        withPlaceHolders.remove(element);
                        res.addAll(getAllDeclarationsWithPlaceHolders((Container) element));
                        whatInheritsFrom.remove(element.getName());
                    }
                }
                //if  unable to find the super class/ interface declaration in current scope, resolve the declaration.
                for (DLanguageIdentifier identifier : whatInheritsFrom.values()) {
                    final PsiElement resolve = identifier.getReference().resolve();
                    res.addAll(getAllDeclarationsWithPlaceHolders((Container) resolve));
                }
            } else {
                res.add(visibleElement);
            }
        }
        return res;

    }
}
