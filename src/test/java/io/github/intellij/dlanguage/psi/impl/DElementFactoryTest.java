package io.github.intellij.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.TokenSet;
import com.intellij.testFramework.LightPlatform4TestCase;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.psi.DLanguageAliasDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangModuleDeclaration;
import org.junit.Test;


/**
 * @author Samael Bate (singingbush)
 * created on 20/02/2022
 */
public class DElementFactoryTest extends LightPlatform4TestCase {

    @Test
    public void createDLanguageIdentifierFromText() {
        final PsiElement result = DElementFactory.createDLanguageIdentifierFromText(getProject(), "text");

        assertEquals(DLanguage.INSTANCE, result.getLanguage());
        assertEquals("text", result.getText());
        assertEquals("text", result.getText());
        assertEquals("ID", result.getNode().getElementType().getDebugName());
    }

    @Test
    public void createDLanguageIdentifierFromText_EmptyString() {
        assertNull(DElementFactory.createDLanguageIdentifierFromText(getProject(), ""));
    }

    @Test
    public void createDLanguageModuleFromText() {
        final DlangModuleDeclaration result = DElementFactory.createDLanguageModuleFromText(getProject(), "mymodule");

        assertEquals(DLanguage.INSTANCE, result.getLanguage());
        assertEquals("module mymodule;", result.getText());
        assertEquals("MODULE_DECLARATION", result.getNavigationElement().getNode().getElementType().getDebugName());
    }

    @Test
    public void createDLanguageModuleFromText_EmptyString() {
        assertNull(DElementFactory.createDLanguageModuleFromText(getProject(), ""));
    }

    @Test // alias myint = abc.Foo.bar;
    public void createAliasDeclarationFromText_1() {
        final DLanguageAliasDeclaration result = DElementFactory.createAliasDeclarationFromText(getProject(), "alias myint = abc.Foo.bar;");

        assertEquals("alias myint = abc.Foo.bar;", result.getText());
        assertEquals(result.getOriginalElement(), result.getNavigationElement());
        assertEquals(DLanguage.INSTANCE, result.getLanguage());
        assertEquals("ALIAS_DECLARATION", result.getNode().getElementType().getDebugName());

        final ASTNode[] children = result.getNode().getChildren(TokenSet.ANY);
        assertEquals("alias", children[0].getElementType().getDebugName());
        assertEquals("WHITE_SPACE", children[1].getElementType().getDebugName());
        assertEquals("ALIAS_INITIALIZER", children[2].getElementType().getDebugName());
        assertEquals(";", children[3].getElementType().getDebugName());
    }

    @Test // alias fp = i => 1;
    public void createAliasDeclarationFromText_2() {
        final DLanguageAliasDeclaration result = DElementFactory.createAliasDeclarationFromText(getProject(), "alias fp = i => 1;");

        assertEquals("alias fp = i => 1;", result.getText());
        assertEquals(result.getOriginalElement(), result.getNavigationElement());
        assertEquals(DLanguage.INSTANCE, result.getLanguage());
        assertEquals("ALIAS_DECLARATION", result.getNode().getElementType().getDebugName());

        final ASTNode[] children = result.getNode().getChildren(TokenSet.ANY);
        assertEquals("alias", children[0].getElementType().getDebugName());
        assertEquals("WHITE_SPACE", children[1].getElementType().getDebugName());
        assertEquals("ALIAS_INITIALIZER", children[2].getElementType().getDebugName());
        assertEquals(";", children[3].getElementType().getDebugName());
    }

    @Test // alias fp = (i) { return 1; };
    public void createAliasDeclarationFromText_3() {
        final DLanguageAliasDeclaration result = DElementFactory.createAliasDeclarationFromText(getProject(), "alias fp = (i) { return 1; };");

        assertEquals("alias fp = (i) { return 1; };", result.getText());
        assertEquals(result.getOriginalElement(), result.getNavigationElement());
        assertEquals(DLanguage.INSTANCE, result.getLanguage());
        assertEquals("ALIAS_DECLARATION", result.getNode().getElementType().getDebugName());

        final ASTNode[] children = result.getNode().getChildren(TokenSet.ANY);
        assertEquals("alias", children[0].getElementType().getDebugName());
        assertEquals("WHITE_SPACE", children[1].getElementType().getDebugName());
        assertEquals("ALIAS_INITIALIZER", children[2].getElementType().getDebugName());
        assertEquals(";", children[3].getElementType().getDebugName());
    }

    @Test // alias b = Alias!4;
    public void createAliasDeclarationFromText_4() {
        final DLanguageAliasDeclaration result = DElementFactory.createAliasDeclarationFromText(getProject(), "alias b = Alias!4;");

        assertEquals("alias b = Alias!4;", result.getText());
        assertEquals(result.getOriginalElement(), result.getNavigationElement());
        assertEquals(DLanguage.INSTANCE, result.getLanguage());
        assertEquals("ALIAS_DECLARATION", result.getNode().getElementType().getDebugName());

        final ASTNode[] children = result.getNode().getChildren(TokenSet.ANY);
        assertEquals("alias", children[0].getElementType().getDebugName());
        assertEquals("WHITE_SPACE", children[1].getElementType().getDebugName());
        assertEquals("ALIAS_INITIALIZER", children[2].getElementType().getDebugName());
        assertEquals(";", children[3].getElementType().getDebugName());
    }

    @Test // alias TL = AliasSeq!(int, double); // in previous versions of Phobos, this was known as TypeTuple
    public void createAliasDeclarationFromText_5() {
        final DLanguageAliasDeclaration result = DElementFactory.createAliasDeclarationFromText(getProject(), "alias TL = AliasSeq!(int, double);");

        assertEquals("alias TL = AliasSeq!(int, double);", result.getText());
        assertEquals(result.getOriginalElement(), result.getNavigationElement());
        assertEquals(DLanguage.INSTANCE, result.getLanguage());
        assertEquals("ALIAS_DECLARATION", result.getNode().getElementType().getDebugName());

        final ASTNode[] children = result.getNode().getChildren(TokenSet.ANY);
        assertEquals("alias", children[0].getElementType().getDebugName());
        assertEquals("WHITE_SPACE", children[1].getElementType().getDebugName());
        assertEquals("ALIAS_INITIALIZER", children[2].getElementType().getDebugName());
        assertEquals(";", children[3].getElementType().getDebugName());
    }

    @Test
    public void createAliasDeclarationFromText_EmptyString() {
        assertNull(DElementFactory.createAliasDeclarationFromText(getProject(), ""));
    }
}
