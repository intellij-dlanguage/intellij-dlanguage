package net.masterthought.dlanguage.psi.interfaces;


/**
 * Created by francis on 2/28/2017.
 */
@Deprecated
public interface HasProperty {
    boolean isPropertyFunction();

    boolean hasCustomProperty();


    boolean isSafe();

    boolean isTrusted();

    boolean isNoGC();

    boolean isSystem();
}
