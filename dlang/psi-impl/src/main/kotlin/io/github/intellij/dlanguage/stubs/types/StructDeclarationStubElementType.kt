package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DlangStructDeclarationImpl
import io.github.intellij.dlanguage.psi.named.DlangStructDeclaration
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DlangStructDeclarationStub
import java.io.IOException

class StructDeclarationStubElementType(debugName: String) :
    DNamedStubElementType<DlangStructDeclarationStub, DlangStructDeclaration>(debugName) {
    override fun createPsi(stub: DlangStructDeclarationStub): DlangStructDeclaration {
        return DlangStructDeclarationImpl(stub, this)
    }

    override fun createStub(psi: DlangStructDeclaration, parentStub: StubElement<*>?): DlangStructDeclarationStub {
        return DlangStructDeclarationStub(parentStub, this, psi.getName(), psi.getAttributes())
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangStructDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.getName())
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DlangStructDeclarationStub {
        return DlangStructDeclarationStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
