package io.github.intellij.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.NamedStubBase
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.stubs.index.DAllNameIndex
import io.github.intellij.dlanguage.stubs.index.DMembersIndex
import io.github.intellij.dlanguage.stubs.index.DModuleDeclarationIndex
import io.github.intellij.dlanguage.stubs.index.DTopLevelDeclarationIndex
import io.github.intellij.dlanguage.stubs.index.DTopLevelDeclarationsByModule

abstract class DNamedStubElementType<S : NamedStubBase<T>, T : DNamedElement>(debugName: String) :
    DStubElementType<S, T>(debugName) {

    override fun indexStub(stub: S, sink: IndexSink) {
        val name = stub.name
        if (name == null || name.isBlank()) {
            return
        }
        sink.occurrence<DNamedElement?, String?>(DAllNameIndex.KEY, name)
        DModuleDeclarationIndex.Companion.indexModuleDeclarations<S>(stub, sink, name)
        DTopLevelDeclarationIndex.Companion.indexTopLevelDeclarations<S, T>(stub, sink, name)
        DTopLevelDeclarationsByModule.Companion.indexTopLevelDeclarationsByModule<S, T>(stub, sink)
        DMembersIndex.Companion.indexMembers<S>(stub, sink)
    }

    override fun shouldCreateStub(node: ASTNode): Boolean {
        return (node.psi as DNamedElement).hasAName()
    }
}

