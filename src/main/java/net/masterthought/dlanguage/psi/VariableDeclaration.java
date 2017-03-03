package net.masterthought.dlanguage.psi;

/**
 * Created by francis on 3/1/2017.
 * Contains methods common to auto declarations and variable declarations
 */
public interface VariableDeclaration {

    /**
     * This method is needed because the bnf grammar cannot/does not always distinguish between x = 0; and int x = 0;
     *
     * @return whether or not this is a true variable declaration or a variable initialization only
     */
    boolean actuallyIsDeclaration();

    DLanguageType getType();


}
