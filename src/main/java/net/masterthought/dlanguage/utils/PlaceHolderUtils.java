package net.masterthought.dlanguage.utils;

import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.DLanguageIdentifier;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.Mixin;
import net.masterthought.dlanguage.psi.interfaces.containers.Container;
import net.masterthought.dlanguage.psi.interfaces.containers.InheritancePlaceHolder;
import net.masterthought.dlanguage.psi.interfaces.containers.MixinPlaceHolder;
import net.masterthought.dlanguage.psi.interfaces.containers.PlaceHolder;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.masterthought.dlanguage.psi.interfaces.containers.ContainerUtil.getAllDeclarationsWithPlaceHolders;

/**
 * Created by francis on 3/17/2017.
 */
public class PlaceHolderUtils {
    static List<DNamedElement> fillPlaceHolders(List<DNamedElement> withPlaceHolders) {
        List<DNamedElement> res = new ArrayList<>();
        res.addAll(fillInheritancePlaceHolders(withPlaceHolders));
        res.addAll(fillMixinPlaceHolders(withPlaceHolders));
        if (containsPlaceHolders(res)) {
            return fillPlaceHolders(res);
        }
        return res;
    }

    @Contract(pure = true)
    private static boolean containsPlaceHolders(List<DNamedElement> list) {
        for (DNamedElement dNamedElement : list) {
            if (dNamedElement instanceof PlaceHolder)
                return true;
        }
        return false;
    }

    private static List<DNamedElement> fillMixinPlaceHolders(List<DNamedElement> withPlaceHolders) {
        List<DNamedElement> res = new ArrayList<>();
        for (DNamedElement visibleElement : withPlaceHolders) {
            if (visibleElement instanceof MixinPlaceHolder) {
                final Mixin mixin = ((MixinPlaceHolder) visibleElement).getMixin();
                final String name = mixin.getName();
                //search for definition
                for (DNamedElement element : withPlaceHolders) {
                    if (element.getName().equals(name)) {
                        res.addAll(getAllDeclarationsWithPlaceHolders((Container) element));
                    }
                }
            }
        }
        return res;

    }

    private static List<DNamedElement> fillInheritancePlaceHolders(List<DNamedElement> withPlaceHolders) {
        List<DNamedElement> res = new ArrayList<>();
        for (DNamedElement visibleElement : withPlaceHolders) {
            if (visibleElement instanceof InheritancePlaceHolder) {
                final InheritancePlaceHolder placeHolder = (InheritancePlaceHolder) visibleElement;
                Map<String, DLanguageIdentifier> whatInheritsFrom = placeHolder.getSuperClassNames();
                //search for declarations which resolve the class declaration. This could foreseeable cause bugs in the distant future, if there where two classes/interfaces with the same name and both visible from here todo
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
