package net.masterthought.dlanguage.psi;

/**
 * Created by francis on 2/28/2017.
 */
public interface HasVisibility extends DNamedElement {
    boolean isPublic();

    boolean isPrivate();

    boolean isProtected();

}
