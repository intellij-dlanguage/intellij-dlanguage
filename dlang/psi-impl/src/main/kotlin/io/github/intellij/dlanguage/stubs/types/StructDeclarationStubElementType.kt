package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DlangStructDeclarationImpl
import io.github.intellij.dlanguage.psi.named.DLanguageStructDeclaration
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DLanguageStructDeclarationStub
import java.io.IOException

class StructDeclarationStubElementType(debugName: String) :
    DNamedStubElementType<DLanguageStructDeclarationStub, DLanguageStructDeclaration>(debugName) {
    override fun createPsi(stub: DLanguageStructDeclarationStub): DLanguageStructDeclaration {
        return DlangStructDeclarationImpl(stub, this)
    }

    override fun createStub(psi: DLanguageStructDeclaration, parentStub: StubElement<*>?): DLanguageStructDeclarationStub {
        return DLanguageStructDeclarationStub(parentStub, this, psi.getName(), psi.getAttributes())
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageStructDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.getName())
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DLanguageStructDeclarationStub {
        return DLanguageStructDeclarationStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
