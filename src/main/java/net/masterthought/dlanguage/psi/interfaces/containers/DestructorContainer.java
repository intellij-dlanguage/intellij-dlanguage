package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageDestructor;
import net.masterthought.dlanguage.psi.DLanguageSharedStaticDestructor;
import net.masterthought.dlanguage.psi.DLanguageStaticDestructor;

/**
 * Created by francis on 3/8/2017.
 */
public interface DestructorContainer extends Container {
    Class staticsharedDestructorClass = DLanguageSharedStaticDestructor.class;
    Class staticDestructorClass = DLanguageStaticDestructor.class;
    Class destructorClass = DLanguageDestructor.class;
}
