package net.masterthought.dlanguage.psi.interfaces;

import net.masterthought.dlanguage.psi.DLanguageIdentifier;

import java.util.List;
import java.util.Map;

/**
 * Created by francis on 3/6/2017.
 */
@Deprecated
public interface CanInherit extends DNamedElement {
    List<CanInherit> whatInheritsFrom();

    Map<String, DLanguageIdentifier> getSuperClassNames();
}
