package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.Nullable;

/**
 * Interface that combines everything we need for convenient navigation.
 */
// The PsiNameIdentifierOwner is necessary for the in-place rename refactoring.
// PsiNamedElement seems like it should be enough, but it's not.
public interface DNamedElement extends DCompositeElement, PsiNameIdentifierOwner, NavigationItem {

//    default String getFullName() {
//        return DPsiImplUtil.getFullName(this);
//    }

    @Nullable
    @Override
    default PsiElement getNameIdentifier() {
        ASTNode keyNode = getNode();
        return keyNode != null ? keyNode.getPsi() : null;
    }
}
