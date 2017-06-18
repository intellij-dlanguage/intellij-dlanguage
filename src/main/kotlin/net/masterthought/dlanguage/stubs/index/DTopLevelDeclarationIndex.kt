package net.masterthought.dlanguage.stubs.index

import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey
import net.masterthought.dlanguage.psi.interfaces.Declaration

class DTopLevelDeclarationIndex : StringStubIndexExtension<Declaration>() {

    override fun getVersion(): Int {
        return super.getVersion() + VERSION
    }

    override fun getKey(): StubIndexKey<String, Declaration> {
        return KEY
    }

    companion object {
        val KEY: StubIndexKey<String, Declaration> = StubIndexKey.createIndexKey<String, Declaration>("d.globally.accessible.name")
        val VERSION = 4
    }
}

