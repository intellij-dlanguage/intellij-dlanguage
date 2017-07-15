package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import net.masterthought.dlanguage.psi.impl.DPsiImplUtil;
import net.masterthought.dlanguage.psi.interfaces.containers.Container;
import org.jetbrains.annotations.Nullable;

/**
 * Interface that combines everything we need for convenient navigation.
 */
// The PsiNameIdentifierOwner is necessary for the in-place rename refactoring.
// PsiNamedElement seems like it should be enough, but it's not.
public interface DNamedElement extends DCompositeElement, PsiNameIdentifierOwner, NavigationItem {
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

    default String getFullName() {
        return DPsiImplUtil.getFullName(this);
    }

    @Nullable
    @Override
    default PsiElement getNameIdentifier() {
        ASTNode keyNode = getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }
}
