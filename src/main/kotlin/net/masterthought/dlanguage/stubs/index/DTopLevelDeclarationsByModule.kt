package net.masterthought.dlanguage.stubs.index

import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.NamedStubBase
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey
import net.masterthought.dlanguage.psi.DLanguageFile
import net.masterthought.dlanguage.psi.interfaces.DNamedElement
import net.masterthought.dlanguage.stubs.DLanguageIdentifierStub

/**
 * Created by francis on 6/17/2017.
 */
class DTopLevelDeclarationsByModule : StringStubIndexExtension<DNamedElement>() {
    override fun getKey(): StubIndexKey<String, DNamedElement> {
        return KEY
    }

    override fun getVersion(): Int {
        return VERSION
    }

    companion object {
        val KEY: StubIndexKey<String, DNamedElement> = StubIndexKey.createIndexKey<String, DNamedElement>("d.globally.accessible.module")
        val VERSION = 2
        fun <S : NamedStubBase<T>, T : DNamedElement> indexTopLevelDeclarationsByModule(stub: S, sink: IndexSink) {
            if (stub !is DLanguageIdentifierStub && topLevelDeclaration(stub)) {
                val fileName = (stub.psi.containingFile as DLanguageFile).moduleOrFileName
                sink.occurrence(DTopLevelDeclarationsByModule.KEY, fileName)
            }
        }
    }
}
