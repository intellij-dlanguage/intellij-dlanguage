package net.masterthought.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import net.masterthought.dlanguage.psi.DlangUnionDeclaration
import net.masterthought.dlanguage.psi.impl.named.DlangUnionDeclarationImpl
import net.masterthought.dlanguage.stubs.DlangUnionDeclarationStub
import net.masterthought.dlanguage.utils.DUtil

import java.io.IOException

class DlangUnionDeclarationStubElementType(debugName: String) : DNamedStubElementType<DlangUnionDeclarationStub, DlangUnionDeclaration>(debugName) {

    override fun createPsi(stub: DlangUnionDeclarationStub): DlangUnionDeclaration {
        return DlangUnionDeclarationImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode?): Boolean {
        return DUtil.definitionNode(node!!)
    }

    override fun createStub(psi: DlangUnionDeclaration, parentStub: StubElement<*>): DlangUnionDeclarationStub {
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
