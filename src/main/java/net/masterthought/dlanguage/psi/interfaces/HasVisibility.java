package net.masterthought.dlanguage.psi.interfaces;

/**
 * Created by francis on 2/28/2017.
 */
public interface HasVisibility extends DNamedElement {
    boolean isSomeVisibility(Visibility visibility);

    default boolean isPublic() {
        return isSomeVisibility(Visibility.public_);
    }

    default boolean isPrivate() {
        return isSomeVisibility(Visibility.private_);
    }

    default boolean isProtected() {
        return isSomeVisibility(Visibility.protected_);
    }

    enum Visibility {
        public_,
        protected_,
        private_,
    }

}
