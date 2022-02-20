package io.github.intellij.dlanguage.psi.impl;

import com.intellij.psi.PsiElement;
import com.intellij.testFramework.LightPlatform4TestCase;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.psi.DLanguageAliasDeclaration;
import io.github.intellij.dlanguage.psi.DLanguageImportDeclaration;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageModuleDeclarationImpl;
import io.github.intellij.dlanguage.psi.named.DLanguageModuleDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Samael Bate (singingbush)
 * created on 20/02/2022
 */
public class DElementFactoryTest extends LightPlatform4TestCase {

    @Test
    public void createDLanguageIdentifierFromText() {
        final DlangIdentifier result = DElementFactory.createDLanguageIdentifierFromText(getProject(), "text");

        assertEquals(DLanguage.INSTANCE, result.getLanguage());
        assertEquals("text", result.getText());
        assertEquals("text", result.getName());
        assertEquals("IDENTIFIER", result.getElementType().getDebugName());
    }

    @Test
    public void createDLanguageIdentifierFromText_EmptyString() {
        assertNull(DElementFactory.createDLanguageIdentifierFromText(getProject(), ""));
    }

    @Test
    public void createDLanguageModuleFromText() {
        final DLanguageModuleDeclaration result = DElementFactory.createDLanguageModuleFromText(getProject(), "mymodule");

        assertEquals(DLanguage.INSTANCE, result.getLanguage());
        assertEquals("module mymodule;", result.getText());
        assertEquals("MODULE_DECLARATION", result.getNavigationElement().getNode().getElementType().getDebugName());
    }

    @Test
    public void createDLanguageModuleFromText_EmptyString() {
        assertNull(DElementFactory.createDLanguageModuleFromText(getProject(), ""));
    }

//    @Test
//    public void createDLanguageSingleImportFromText_Foo() {
//        final DLanguageImportDeclaration result = DElementFactory.createDLanguageSingleImportFromText(getProject(), "foo");
//
//        assertEquals("import foo;", result.getText());
//        assertEquals("foo", result.getNavigationElement().getText());
//        assertEquals(DLanguage.INSTANCE, result.getNavigationElement().getLanguage());
//    }
//
//    @Test
//    public void createDLanguageSingleImportFromText_FooBar() {
//        final DLanguageImportDeclaration result = DElementFactory.createDLanguageSingleImportFromText(getProject(), "foo.bar");
//
//        assertEquals("import foo.bar;", result.getText());
//        assertEquals("foo.bar", result.getNavigationElement().getText());
//        assertEquals(DLanguage.INSTANCE, result.getNavigationElement().getLanguage());
//    }
//
//    @Test
//    public void createDLanguageSingleImportFromText_EmptyString() {
//        assertNull(DElementFactory.createDLanguageSingleImportFromText(getProject(), ""));
//    }

    @Test // alias myint = abc.Foo.bar;
    public void createAliasDeclarationFromText_1() {
        // alias myint = abc.Foo.bar;
        // alias fp = i => 1;
        // alias fp = (i) { return 1; };
        //
        // aliasing can also be done using std.meta.Alias and std.meta.AliasSeq
        // https://dlang.org/library/std/meta/alias.html
        //
        // alias b = Alias!4; // need to use std.meta.Alias to create alias of a literal
        // alias TL = AliasSeq!(int, double); // in previous versions of Phobos, this was known as TypeTuple.

        final DLanguageAliasDeclaration result = DElementFactory.createAliasDeclarationFromText(getProject(), "alias myint = abc.Foo.bar;");

        assertEquals("alias myint = abc.Foo.bar;", result.getText());
        assertEquals(result.getOriginalElement(), result.getNavigationElement());
        assertEquals(DLanguage.INSTANCE, result.getLanguage());
        assertEquals("ALIAS_DECLARATION", result.getNavigationElement().getNode().getElementType().getDebugName());
    }

    @Test // alias fp = i => 1;
    public void createAliasDeclarationFromText_2() {
        final DLanguageAliasDeclaration result = DElementFactory.createAliasDeclarationFromText(getProject(), "alias fp = i => 1;");

        assertEquals("alias fp = i => 1;", result.getText());
        assertEquals(result.getOriginalElement(), result.getNavigationElement());
        assertEquals(DLanguage.INSTANCE, result.getLanguage());
        assertEquals("ALIAS_DECLARATION", result.getNavigationElement().getNode().getElementType().getDebugName());
    }

    @Test // alias fp = (i) { return 1; };
    public void createAliasDeclarationFromText_3() {
        final DLanguageAliasDeclaration result = DElementFactory.createAliasDeclarationFromText(getProject(), "alias fp = (i) { return 1; };");

        assertEquals("alias fp = (i) { return 1; };", result.getText());
        assertEquals(result.getOriginalElement(), result.getNavigationElement());
        assertEquals(DLanguage.INSTANCE, result.getLanguage());
        assertEquals("ALIAS_DECLARATION", result.getNavigationElement().getNode().getElementType().getDebugName());
    }

    @Test // alias b = Alias!4;
    public void createAliasDeclarationFromText_4() {
        final DLanguageAliasDeclaration result = DElementFactory.createAliasDeclarationFromText(getProject(), "alias b = Alias!4;");

        assertEquals("alias b = Alias!4;", result.getText());
        assertEquals(result.getOriginalElement(), result.getNavigationElement());
        assertEquals(DLanguage.INSTANCE, result.getLanguage());
        assertEquals("ALIAS_DECLARATION", result.getNavigationElement().getNode().getElementType().getDebugName());
    }

    @Test
    public void createAliasDeclarationFromText_EmptyString() {
        assertNull(DElementFactory.createAliasDeclarationFromText(getProject(), ""));
    }
}
