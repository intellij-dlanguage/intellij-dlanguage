package io.github.intellij.dlanguage.stubs.index

import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.*
import io.github.intellij.dlanguage.index.DModuleIndex
import io.github.intellij.dlanguage.index.DModuleIndex.getFilesByModuleName
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.stubs.DlangIdentifierStub

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
            if (stub !is io.github.intellij.dlanguage.stubs.DlangIdentifierStub && topLevelDeclaration<S, T>(stub)) {
                sink.occurrence(DTopLevelDeclarationIndex.KEY, name)
            }
        }

        fun getTopLevelSymbols(name: String, module: String, project: Project): MutableSet<DNamedElement> {
            val elements = mutableSetOf<DNamedElement>()
            for (file in getFilesByModuleName(project, module, GlobalSearchScope.everythingScope(project))) {
                elements.addAll(StubIndex.getElements(KEY, name, project, GlobalSearchScope.fileScope(file), DNamedElement::class.java))
            }
            return elements
        }

    }
}

