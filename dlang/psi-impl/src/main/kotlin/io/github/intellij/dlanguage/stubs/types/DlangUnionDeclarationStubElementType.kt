package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.named.DlangUnionDeclaration
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.stubs.DlangUnionDeclarationStub
import io.github.intellij.dlanguage.stubs.types.DNamedStubElementType
import java.io.IOException

class DlangUnionDeclarationStubElementType(debugName: String) : DNamedStubElementType<DlangUnionDeclarationStub, DlangUnionDeclaration>(debugName) {

    override fun createPsi(stub: io.github.intellij.dlanguage.stubs.DlangUnionDeclarationStub): DlangUnionDeclaration {
        return io.github.intellij.dlanguage.psi.impl.named.DlangUnionDeclarationImpl(stub, this)
    }

    override fun createStub(psi: DlangUnionDeclaration, parentStub: StubElement<*>): io.github.intellij.dlanguage.stubs.DlangUnionDeclarationStub {
        return io.github.intellij.dlanguage.stubs.DlangUnionDeclarationStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: io.github.intellij.dlanguage.stubs.DlangUnionDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): io.github.intellij.dlanguage.stubs.DlangUnionDeclarationStub {
        return io.github.intellij.dlanguage.stubs.DlangUnionDeclarationStub(
            parentStub, this, dataStream.readName()!!,
            DAttributes.read(dataStream)
        )
    }
}
