package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageConstructor;
import net.masterthought.dlanguage.psi.DLanguageSharedStaticConstructor;
import net.masterthought.dlanguage.psi.DLanguageStaticConstructor;

/**
 * Created by francis on 3/8/2017.
 */
public interface ConstructorContainer extends Container {
    Class staticsharedConstructorClass = DLanguageSharedStaticConstructor.class;
    Class staticConstructorClass = DLanguageStaticConstructor.class;
    Class constructorClass = DLanguageConstructor.class;

}
