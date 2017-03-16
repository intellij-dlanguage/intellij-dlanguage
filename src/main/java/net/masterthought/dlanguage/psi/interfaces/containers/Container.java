package net.masterthought.dlanguage.psi.interfaces.containers;

import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;

import java.util.List;


/**
 * Created by francis on 3/7/2017.
 */
public interface Container extends DNamedElement, PsiElement {
    /**
     * todo can't be called get declarations because conflicts with static imports in child classes
     *
     * @param declarationClass
     * @param includeFromMixins
     * @param includeFromInheritance
     * @param includeNestedDeclarations
     * @param <DeclarationClass>
     * @return
     */
    default <DeclarationClass extends DNamedElement> List<? extends DeclarationClass> getDeclarationsContainer(Class<DeclarationClass> declarationClass, boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return ContainerUtil.getDeclarations(this, this, declarationClass, includeFromMixins, includeFromInheritance, includeNestedDeclarations);
    }

    default Container getParentContainer() {
        PsiElement current = this.getParent();
        while (true) {
            if (current != this && current instanceof Container)
                return (Container) current;
            if (current.getParent() == null)
                return null;
            current = current.getParent();
        }
    }


//    default List<? extends DNamedElement> getDeclarationsOfTypes(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations,Class<DNamedElement>... declarationClass){
//
//    }
//    default List<DNamedElement> getAllDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations){
//
//    }
}
