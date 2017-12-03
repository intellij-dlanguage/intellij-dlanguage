package io.github.intellij.dlanguage.resolve.processors.parameters

import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.psi.*
import io.github.intellij.dlanguage.utils.InterfaceOrClass
import io.kotlintest.mock.`when`
import io.kotlintest.mock.mock
import junit.framework.TestCase
import org.junit.Test

/**
 * @author Samael Bate (singingbush)
 * created on 13/11/17
 */
class DAttributesFinderTest : TestCase() {

    @Test
    fun testDAttributesFinderDefaults() {
        val startingPoint = mock<PsiElement>()
        val finder = DAttributesFinder(startingPoint)

//        assertTrue("defaultsToPublic", finder.defaultsToPublic)
//        assertTrue("defaultsToStatic", finder.defaultsToStatic)
//
//        assertFalse("defaultsToConst", finder.defaultsToConst)
//        assertFalse("defaultsToExtern", finder.defaultsToExtern)
//        assertFalse("defaultsToImmutable", finder.defaultsToImmutable)
//        assertFalse("defaultsToLocal", finder.defaultsToLocal)
//        assertFalse("defaultsToNoGC", finder.defaultsToNoGC)
//        assertFalse("defaultsToNothrow", finder.defaultsToNothrow)
//        assertFalse("defaultsToPrivate", finder.defaultsToPrivate)
//        assertFalse("defaultsToProperty", finder.defaultsToProperty)
//        assertFalse("defaultsToProtected", finder.defaultsToProtected)
//        assertFalse("defaultsToPure", finder.defaultsToPure)
//
        assertTrue("isPublic", finder.isPublic())
        assertFalse("isPrivate", finder.isPrivate())
        assertFalse("isProtected", finder.isProtected())

        assertTrue("isStatic", finder.isStatic())
        assertFalse("isProperty", finder.isProperty())
        assertFalse("isNoGC", finder.isNoGC())
        assertFalse("isExtern", finder.isExtern())
        assertFalse("isPure", finder.isPure())
        assertFalse("isNothrow", finder.isNothrow())
    }

    @Test
    fun testRecurseUpConstructor() {
        val startingPoint = mock<DlangConstructor>()
        val psi = mock<PsiElement>()
        `when`(startingPoint.kW_THIS).thenReturn(psi)

        val finder = DAttributesFinder(startingPoint)

        finder.recurseUp()
    }

    @Test
    fun testRecurseUpFunctionDeclaration() {
        val startingPoint = mock<DlangFunctionDeclaration>()
        val identifier = mock<DlangIdentifier>()
        `when`(startingPoint.identifier).thenReturn(identifier)

        val finder = DAttributesFinder(startingPoint)

        finder.recurseUp()
    }

    @Test
    fun testRecurseUpInterfaceOrClass() {
        val attribute = mock<DLanguageAttribute>()
        val atAttribute = mock<DLanguageAtAttribute>()
        val property = mock<DlangIdentifier>()
        `when`(attribute.atAttribute).thenReturn(atAttribute)
        `when`(attribute.textOffset).thenReturn(1)
        `when`(atAttribute.identifier).thenReturn(property)
        `when`(property.name).thenReturn("property")

        val startingPoint = mock<InterfaceOrClass>()
        val identifier = mock<DlangIdentifier>()
        `when`(identifier.parent).thenReturn(attribute)
        `when`(startingPoint.identifier).thenReturn(identifier)
        `when`(startingPoint.textOffset).thenReturn(5)

        val finder = DAttributesFinder(startingPoint)

        assertFalse(finder.isProperty())

        finder.recurseUp()

        assertTrue(finder.isProperty())
    }

}
