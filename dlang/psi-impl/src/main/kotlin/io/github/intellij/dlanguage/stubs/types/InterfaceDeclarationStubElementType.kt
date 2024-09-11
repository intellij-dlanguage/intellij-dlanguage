package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DlangInterfaceDeclarationImpl
import io.github.intellij.dlanguage.psi.named.DLanguageInterfaceDeclaration
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DLanguageInterfaceDeclarationStub
import java.io.IOException

class InterfaceDeclarationStubElementType(debugName: String) :
    DNamedStubElementType<DLanguageInterfaceDeclarationStub, DLanguageInterfaceDeclaration>(debugName) {
    override fun createPsi(stub: DLanguageInterfaceDeclarationStub): DLanguageInterfaceDeclaration {
        return DlangInterfaceDeclarationImpl(stub, this)
    }

    override fun createStub(
        psi: DLanguageInterfaceDeclaration,
        parentStub: StubElement<*>?
    ): DLanguageInterfaceDeclarationStub {
        return DLanguageInterfaceDeclarationStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageInterfaceDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DLanguageInterfaceDeclarationStub {
        return DLanguageInterfaceDeclarationStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
