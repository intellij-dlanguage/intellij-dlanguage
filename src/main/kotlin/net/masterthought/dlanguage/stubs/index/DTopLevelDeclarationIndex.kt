package net.masterthought.dlanguage.stubs.index

import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.NamedStubBase
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey
import net.masterthought.dlanguage.psi.interfaces.DNamedElement
import net.masterthought.dlanguage.stubs.DLanguageIdentifierStub

class DTopLevelDeclarationIndex : StringStubIndexExtension<DNamedElement>() {

    override fun getVersion(): Int {
        return super.getVersion() + VERSION
    }

    override fun getKey(): StubIndexKey<String, DNamedElement> {
        return KEY
    }

    companion object {
        val KEY: StubIndexKey<String, DNamedElement> = StubIndexKey.createIndexKey<String, DNamedElement>("d.globally.accessible.name")
        val VERSION = 6
        fun <S : NamedStubBase<T>, T : DNamedElement> indexTopLevelDeclarations(stub: S, sink: IndexSink, name: String) {
            if (stub !is DLanguageIdentifierStub && topLevelDeclaration<S, T>(stub)) {
                sink.occurrence(DTopLevelDeclarationIndex.KEY, name)
            }
        }

    }
}

