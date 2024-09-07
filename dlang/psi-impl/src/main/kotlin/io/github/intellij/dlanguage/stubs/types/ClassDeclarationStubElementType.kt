package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DlangClassDeclarationImpl
import io.github.intellij.dlanguage.psi.named.DlangClassDeclaration
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DlangClassDeclarationStub
import java.io.IOException

class ClassDeclarationStubElementType(debugName: String) :
    DNamedStubElementType<DlangClassDeclarationStub, DlangClassDeclaration>(debugName) {
    override fun createPsi(stub: DlangClassDeclarationStub): DlangClassDeclaration {
        return DlangClassDeclarationImpl(stub, this)
    }

    override fun createStub(psi: DlangClassDeclaration, parentStub: StubElement<*>?): DlangClassDeclarationStub {
        return DlangClassDeclarationStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangClassDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DlangClassDeclarationStub {
        return DlangClassDeclarationStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
