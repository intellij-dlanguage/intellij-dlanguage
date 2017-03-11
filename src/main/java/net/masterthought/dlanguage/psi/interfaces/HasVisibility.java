package net.masterthought.dlanguage.psi.interfaces;

/**
 * Created by francis on 2/28/2017.
 */
public interface HasVisibility extends DNamedElement {
    //todo should this be an enum
    boolean isSomeVisibility(String visibility);

    default boolean isPublic() {
        return isSomeVisibility("public");
    }

    default boolean isPrivate() {
        return isSomeVisibility("private");
    }

    default boolean isProtected() {
        return isSomeVisibility("protected");
    }

}
