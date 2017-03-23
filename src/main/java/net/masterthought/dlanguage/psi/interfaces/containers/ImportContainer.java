package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageImport;

/**
 * Created by francis on 3/15/2017.
 */
public interface ImportContainer extends Container {
    Class importClass = DLanguageImport.class;//sorry about the confusing name. The class object for class declarations.
}
