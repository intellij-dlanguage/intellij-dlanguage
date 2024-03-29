package io.github.intellij.dlanguage.unittest

import com.intellij.icons.AllIcons
import com.intellij.lang.Language
import com.intellij.openapi.module.Module
import com.intellij.psi.PsiElement
import com.intellij.testIntegration.TestFramework
import io.github.intellij.dlanguage.DLanguage
import javax.swing.Icon

/**
 * A base class to be used by any future D test frameworks.
 *
 * d-unit support provided by DUnitTestFramework.class
 *
 * In the future I'd like to support other options such as:
 *      Unit Threaded (https://code.dlang.org/packages/unit-threaded)
 *      Silly (https://code.dlang.org/packages/silly)
 *      DUnit (https://code.dlang.org/packages/)
 *      trial (https://code.dlang.org/packages/trial)
 *
 * see https://github.com/intellij-dlanguage/intellij-dlanguage/issues/431
 *
 * @author Samael Bate (singingbush)
 * created on 06/11/2019
 */
abstract class DlangTestFramework : TestFramework {

    override fun getLanguage(): Language = DLanguage

    override fun getName(): String = DLanguage.displayName

    override fun getIcon(): Icon = AllIcons.RunConfigurations.Junit

    override fun getLibraryPath(): String? = null // todo: Should be able to get this

    override fun isLibraryAttached(module: Module): Boolean = false

    override fun getDefaultSuperClass(): String? = null

    override fun isPotentialTestClass(clazz: PsiElement): Boolean = isTestClass(clazz)

    override fun isTestClass(clazz: PsiElement): Boolean = true
}
