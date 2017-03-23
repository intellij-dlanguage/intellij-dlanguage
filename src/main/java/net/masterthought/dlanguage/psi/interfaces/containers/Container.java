package net.masterthought.dlanguage.psi.interfaces.containers;

import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;


/**
 * Created by francis on 3/7/2017.
 */
public interface Container extends DNamedElement, PsiElement {

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

}
