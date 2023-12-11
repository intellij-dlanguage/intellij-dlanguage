package io.github.intellij.dlanguage.sdk

import com.intellij.openapi.roots.OrderRootType
import io.github.intellij.dlanguage.LightDlangTestCase
import org.junit.Test

/**
 * Tests for all x3 implementations of DlangSdkType
 *
 * @see io.github.intellij.dlanguage.sdk.DlangDmdSdkType
 * @see io.github.intellij.dlanguage.sdk.DlangGdcSdkType
 * @see io.github.intellij.dlanguage.sdk.DlangLdcSdkType
 */
class DlangSdkTypeTest : LightDlangTestCase() {

    @Test
    fun `test DlangDmdSdkType`() {
        val sdkType = DlangDmdSdkType()

        assertEquals("DMD (Digital Mars D Compiler)", sdkType.getPresentableName())

        assertEquals("https://dlang.org/phobos/index.html", sdkType.getDefaultDocumentationUrl(super.getProjectJDK()))
        assertEquals("https://dlang.org/download.html", sdkType.downloadSdkUrl)

        assertTrue(sdkType.isRootTypeApplicable(OrderRootType.SOURCES))
        assertFalse(sdkType.isRootTypeApplicable(OrderRootType.CLASSES))
        assertFalse(sdkType.isRootTypeApplicable(OrderRootType.DOCUMENTATION))
        assertTrue(sdkType.allowWslSdkForLocalProject())
    }

    @Test
    fun `test DlangGdcSdkType`() {
        val sdkType = DlangGdcSdkType()

        assertEquals("GDC (The GNU D Compiler)", sdkType.getPresentableName())

        assertEquals("https://dlang.org/phobos/index.html", sdkType.getDefaultDocumentationUrl(super.getProjectJDK()))
        assertEquals("https://dlang.org/download.html", sdkType.downloadSdkUrl)

        assertTrue(sdkType.isRootTypeApplicable(OrderRootType.SOURCES))
        assertFalse(sdkType.isRootTypeApplicable(OrderRootType.CLASSES))
        assertFalse(sdkType.isRootTypeApplicable(OrderRootType.DOCUMENTATION))
        assertTrue(sdkType.allowWslSdkForLocalProject())
    }

    @Test
    fun `test DlangLdcSdkType`() {
        val sdkType = DlangLdcSdkType()

        assertEquals("LDC (LLVM-based D Compiler)", sdkType.getPresentableName())

        assertEquals("https://dlang.org/phobos/index.html", sdkType.getDefaultDocumentationUrl(super.getProjectJDK()))
        assertEquals("https://dlang.org/download.html", sdkType.downloadSdkUrl)

        assertTrue(sdkType.isRootTypeApplicable(OrderRootType.SOURCES))
        assertFalse(sdkType.isRootTypeApplicable(OrderRootType.CLASSES))
        assertFalse(sdkType.isRootTypeApplicable(OrderRootType.DOCUMENTATION))
        assertTrue(sdkType.allowWslSdkForLocalProject())
    }
}
