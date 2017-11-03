package io.github.intellij.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.DlangUnionDeclaration
import io.github.intellij.dlanguage.psi.impl.named.DlangUnionDeclarationImpl
import io.github.intellij.dlanguage.stubs.DlangUnionDeclarationStub
import io.github.intellij.dlanguage.utils.DUtil
import io.github.intellij.dlanguage.utils.DUtil.definitionNode

import java.io.IOException

class DlangUnionDeclarationStubElementType(debugName: String) : DNamedStubElementType<DlangUnionDeclarationStub, DlangUnionDeclaration>(debugName) {

    override fun createPsi(stub: DlangUnionDeclarationStub): DlangUnionDeclaration {
        return DlangUnionDeclarationImpl(stub, this)
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
