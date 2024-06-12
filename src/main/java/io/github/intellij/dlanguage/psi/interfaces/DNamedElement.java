package io.github.intellij.dlanguage.psi.interfaces;

import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiNameIdentifierOwner;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributesFinder;
import org.jetbrains.annotations.NotNull;
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

    @NotNull
    @Override
    String getName();

    DAttributes getAttributes();

    default boolean hasAName() {
        return true;
    }

    boolean isPublic();

    boolean isProtected();

    boolean isPrivate();

    DAttributesFinder.Visibility visibility();

    boolean isProperty();

    boolean isNoGC();

    boolean isExtern();

    boolean isPure();

    boolean isNothrow();

    boolean isConst();

    boolean isImmutable();

    boolean isEnum();
}
