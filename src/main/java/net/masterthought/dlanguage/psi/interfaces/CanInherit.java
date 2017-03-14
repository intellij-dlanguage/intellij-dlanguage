package net.masterthought.dlanguage.psi.interfaces;

import net.masterthought.dlanguage.psi.interfaces.containers.Container;
import net.masterthought.dlanguage.psi.interfaces.containers.StatementContainer;

import java.util.List;

/**
 * Created by francis on 3/6/2017.
 */
public interface CanInherit extends StatementContainer, Container, DNamedElement {
    List<CanInherit> whatInheritsFrom();


}
