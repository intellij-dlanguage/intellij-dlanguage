package io.github.intellij.dlanguage.attributes

import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributesFinder

/**
 * @author Samael Bate (singingbush)
 * created on 13/11/17
 */
class DAttributesFinderTest : DAttributesFinderTestCase() {
    fun testPublicImport() {
        return doTest(DAttributes(false, DAttributesFinder.Visibility.PUBLIC, false, false, false, false, false, false, false, false))
    }
}
