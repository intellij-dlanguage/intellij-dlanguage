package io.github.intellij.dlanguage.stubs.index

import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.NamedStubBase
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey
import io.github.intellij.dlanguage.psi.DLanguageModuleDeclaration
import io.github.intellij.dlanguage.utils.ModuleDeclaration

/**
 * Created by francis on 6/16/2017.
 */
class DModuleDeclarationIndex : StringStubIndexExtension<ModuleDeclaration>() {
    override fun getVersion(): Int {
        return super.getVersion() + VERSION
    }

    override fun getKey(): StubIndexKey<String, ModuleDeclaration> {
        return KEY
    }

    companion object {
        val KEY = StubIndexKey.createIndexKey<String, ModuleDeclaration>("d.module")
        val VERSION = 2
        fun <S : NamedStubBase<*>> indexModuleDeclarations(stub: S, sink: IndexSink, name: String) {
            if (stub is io.github.intellij.dlanguage.psi.DLanguageModuleDeclaration) {
                sink.occurrence(DModuleDeclarationIndex.KEY, name)
            }
        }
    }
}


