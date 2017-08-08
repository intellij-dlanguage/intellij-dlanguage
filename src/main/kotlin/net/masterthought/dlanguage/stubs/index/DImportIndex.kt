package net.masterthought.dlanguage.stubs.index

import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey
import net.masterthought.dlanguage.utils.SingleImport

/**
 * Created by francis on 7/25/2017.
 */
class DPublicImportIndex : StringStubIndexExtension<SingleImport>() {

    override fun getVersion(): Int {
        return super.getVersion() + VERSION
    }

    override fun getKey(): StubIndexKey<String, SingleImport> {
        return KEY
    }

    companion object {
        val KEY: StubIndexKey<String, SingleImport> = StubIndexKey.createIndexKey<String, SingleImport>("d.globally.accessible.import")
        val VERSION = 1
    }
}
