package net.masterthought.dlanguage.stubs.index

import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.NamedStubBase
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey
import net.masterthought.dlanguage.psi.DLanguageFile
import net.masterthought.dlanguage.psi.interfaces.DNamedElement
import net.masterthought.dlanguage.stubs.DLanguageSingleImportStub
import net.masterthought.dlanguage.utils.SingleImport

/**
 * Created by francis on 7/25/2017.
 */

//todo handle static imports
class DPublicImportIndex : StringStubIndexExtension<SingleImport>() {

    override fun getVersion(): Int {
        return super.getVersion() + VERSION
    }

    override fun getKey(): StubIndexKey<String, SingleImport> {
        return KEY
    }

    companion object {
        val KEY: StubIndexKey<String, SingleImport> = StubIndexKey.createIndexKey<String, SingleImport>("d.globally.accessible.import.public")
        val VERSION = 2
        fun <S : NamedStubBase<T>, T : DNamedElement> indexPublicImports(stub: S, sink: IndexSink) {
            if (stub is DLanguageSingleImportStub && topLevelDeclaration<S, T>(stub)) {
                if ((stub as DLanguageSingleImportStub).isPublic) {
                    val fileName = (stub.psi.containingFile as DLanguageFile).moduleOrFileName
                    sink.occurrence<SingleImport, String>(DPublicImportIndex.KEY, fileName)
                }
            }
        }
    }
}
