package net.masterthought.dlanguage.psi;

/**
 * Created by francis on 2/28/2017.
 */
public interface HasProperty {
    boolean isPropertyFunction();

    boolean hasCustomProperty();

    DLanguageUserDefinedAttribute getCustomProperty();

    boolean isSafe();

    boolean isTrusted();

    boolean isNoGC();

    boolean isSystem();
}
