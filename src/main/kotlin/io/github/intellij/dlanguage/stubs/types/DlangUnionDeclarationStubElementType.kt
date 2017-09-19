package io.github.intellij.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.DlangUnionDeclaration
import io.github.intellij.dlanguage.psi.impl.named.DlangUnionDeclarationImpl
import io.github.intellij.dlanguage.stubs.DlangUnionDeclarationStub
import io.github.intellij.dlanguage.utils.DUtil

import java.io.IOException

class DlangUnionDeclarationStubElementType(debugName: String) : io.github.intellij.dlanguage.stubs.types.DNamedStubElementType<DlangUnionDeclarationStub, io.github.intellij.dlanguage.psi.DlangUnionDeclaration>(debugName) {

    override fun createPsi(stub: DlangUnionDeclarationStub): io.github.intellij.dlanguage.psi.DlangUnionDeclaration {
        return io.github.intellij.dlanguage.psi.impl.named.DlangUnionDeclarationImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode?): Boolean {
        return io.github.intellij.dlanguage.utils.DUtil.definitionNode(node!!)
    }

    override fun createStub(psi: io.github.intellij.dlanguage.psi.DlangUnionDeclaration, parentStub: StubElement<*>): DlangUnionDeclarationStub {
        return DlangUnionDeclarationStub(parentStub, this, psi.name)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangUnionDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DlangUnionDeclarationStub {
        return DlangUnionDeclarationStub(parentStub, this, dataStream.readName()!!)
    }
}
