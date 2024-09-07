package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DLanguageIdentifierInitializerImpl
import io.github.intellij.dlanguage.psi.named.DLanguageIdentifierInitializer
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DLanguageIdentifierInitializerStub
import java.io.IOException

class IdentifierInitializerStubElementType(debugName: String) :
    DNamedStubElementType<DLanguageIdentifierInitializerStub, DLanguageIdentifierInitializer>(debugName) {
    override fun createPsi(stub: DLanguageIdentifierInitializerStub): DLanguageIdentifierInitializer {
        return DLanguageIdentifierInitializerImpl(stub, this)
    }

    override fun createStub(
        psi: DLanguageIdentifierInitializer,
        parentStub: StubElement<*>?
    ): DLanguageIdentifierInitializerStub {
        return DLanguageIdentifierInitializerStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageIdentifierInitializerStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(
        dataStream: StubInputStream,
        parentStub: StubElement<*>?
    ): DLanguageIdentifierInitializerStub {
        return DLanguageIdentifierInitializerStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
