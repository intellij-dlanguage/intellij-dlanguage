package net.masterthought.dlanguage.psi;

import java.util.List;

/**
 * Created by francis on 2/28/2017.
 */
public interface LocalVarContainer {
    List<VariableDeclaration> getVariableDeclarations();

    List<DLanguageAutoDeclarationY> getAutoVariableDeclarations();

    List<DLanguageVarDeclarator> getNonAutoDeclarations();

}
