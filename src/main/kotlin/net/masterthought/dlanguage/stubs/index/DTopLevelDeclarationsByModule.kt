package net.masterthought.dlanguage.stubs.index

import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey
import net.masterthought.dlanguage.psi.interfaces.Declaration

/**
 * Created by francis on 6/17/2017.
 */
class DTopLevelDeclarationsByModule : StringStubIndexExtension<Declaration>() {
    override fun getKey(): StubIndexKey<String, Declaration> {
        return KEY
    }

    override fun getVersion(): Int {
        return VERSION
    }

    companion object {
        val KEY: StubIndexKey<String, Declaration> = StubIndexKey.createIndexKey<String, Declaration>("d.globally.accessible.module")
        val VERSION = 1
    }
}
