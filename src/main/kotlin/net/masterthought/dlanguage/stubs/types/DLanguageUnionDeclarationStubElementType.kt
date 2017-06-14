package net.masterthought.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import net.masterthought.dlanguage.psi.DLanguageUnionDeclaration
import net.masterthought.dlanguage.psi.impl.DLanguageUnionDeclarationImpl
import net.masterthought.dlanguage.stubs.DLanguageUnionDeclarationStub
import net.masterthought.dlanguage.utils.DUtil

import java.io.IOException

class DLanguageUnionDeclarationStubElementType(debugName: String) : DNamedStubElementType<DLanguageUnionDeclarationStub, DLanguageUnionDeclaration>(debugName) {

    override fun createPsi(stub: DLanguageUnionDeclarationStub): DLanguageUnionDeclaration {
        return DLanguageUnionDeclarationImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode?): Boolean {
        return DUtil.definitionNode(node!!)
    }

    override fun createStub(psi: DLanguageUnionDeclaration, parentStub: StubElement<*>): DLanguageUnionDeclarationStub {
        return DLanguageUnionDeclarationStub(parentStub, this, psi.name)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageUnionDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageUnionDeclarationStub {
        return DLanguageUnionDeclarationStub(parentStub, this, dataStream.readName()!!)
    }
}
