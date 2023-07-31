package io.github.intellij.dub.module

import com.intellij.openapi.projectRoots.impl.UnknownSdkType
import com.intellij.testFramework.UsefulTestCase
import io.github.intellij.dlanguage.DlangSdkType
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DlangDubModuleBuilderTest : UsefulTestCase() {

    private val moduleBuilder = DlangDubModuleBuilder()

    @Test
    fun `test isSuitableSdkType returns true only for DlangSdkType`() {
        assertTrue(moduleBuilder.isSuitableSdkType(DlangSdkType.getInstance()))
        assertFalse(moduleBuilder.isSuitableSdkType(UnknownSdkType.getInstance("some other sdk type")))
    }

    @Test
    fun `test it creates source directory if sourcePaths was empty`() {
        assertNotNull(moduleBuilder.sourcePaths)
        assertEquals(1, moduleBuilder.sourcePaths?.size)
    }
}
