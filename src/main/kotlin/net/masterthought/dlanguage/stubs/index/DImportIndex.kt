package net.masterthought.dlanguage.stubs.index

import com.google.common.collect.Sets
import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.*
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

    /**
     * use this method sparingly. better to implement stuff here, than doing raw queries
     */
    override fun getKey(): StubIndexKey<String, SingleImport> {
        return KEY
    }

    companion object {
        private val KEY: StubIndexKey<String, SingleImport> = StubIndexKey.createIndexKey<String, SingleImport>("d.globally.accessible.import.public")
        val VERSION = 2
        fun <S : NamedStubBase<T>, T : DNamedElement> indexPublicImports(stub: S, sink: IndexSink) {
            if (stub is DLanguageSingleImportStub && topLevelDeclaration<S, T>(stub)) {
                if ((stub as DLanguageSingleImportStub).isPublic) {
                    val fileName = (stub.psi.containingFile as DLanguageFile).moduleOrFileName
                    sink.occurrence<SingleImport, String>(DPublicImportIndex.KEY, fileName)
                }
            }
        }

        fun recursivelyGetAllPublicImports(start: SingleImport): Set<SingleImport> {
            //todo get members if scoped
            return getAllPubliclyImported(setOf(start), start.project)
        }

        //todo change type signature to stubs to force not loading psi
        private fun getAllPubliclyImported(modulesIn: Set<SingleImport>, project: Project): Set<SingleImport> {
            val alreadyProcessed = mutableSetOf<SingleImport>()
            val toProcess = Sets.newHashSet<SingleImport>(modulesIn)
            while (true) {
                val tempSet = mutableSetOf<SingleImport>()
                for (import in toProcess) {
                    if (!alreadyProcessed.contains(import)) {
                        tempSet += StubIndex.getElements(DPublicImportIndex.KEY, import.importedModuleName, project, GlobalSearchScope.everythingScope(project), SingleImport::class.java)
                    }
                }
                alreadyProcessed.addAll(toProcess)
                toProcess.addAll(tempSet)
                if (alreadyProcessed == toProcess)
                    break
            }
            return toProcess
        }

    }
}
