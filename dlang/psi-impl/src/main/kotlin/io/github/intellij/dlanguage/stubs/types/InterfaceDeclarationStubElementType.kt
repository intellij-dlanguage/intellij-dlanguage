package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DlangInterfaceDeclarationImpl
import io.github.intellij.dlanguage.psi.named.DlangInterfaceDeclaration
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DlangInterfaceDeclarationStub
import java.io.IOException

class InterfaceDeclarationStubElementType(debugName: String) :
    DNamedStubElementType<DlangInterfaceDeclarationStub, DlangInterfaceDeclaration>(debugName) {
    override fun createPsi(stub: DlangInterfaceDeclarationStub): DlangInterfaceDeclaration {
        return DlangInterfaceDeclarationImpl(stub, this)
    }

    override fun createStub(
        psi: DlangInterfaceDeclaration,
        parentStub: StubElement<*>?
    ): DlangInterfaceDeclarationStub {
        return DlangInterfaceDeclarationStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangInterfaceDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DlangInterfaceDeclarationStub {
        return DlangInterfaceDeclarationStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
