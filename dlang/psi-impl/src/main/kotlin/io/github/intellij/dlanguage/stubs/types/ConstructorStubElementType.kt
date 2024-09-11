package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DLanguageConstructorImpl
import io.github.intellij.dlanguage.psi.named.DLanguageConstructor
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DLanguageConstructorStub
import java.io.IOException

class ConstructorStubElementType(debugName: String) :
    DNamedStubElementType<DLanguageConstructorStub, DLanguageConstructor>(debugName) {
    override fun createPsi(stub: DLanguageConstructorStub): DLanguageConstructor {
        return DLanguageConstructorImpl(stub, this)
    }

    override fun createStub(
        psi: DLanguageConstructor,
        parentStub: StubElement<*>?
    ): DLanguageConstructorStub {
        return DLanguageConstructorStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageConstructorStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DLanguageConstructorStub {
        return DLanguageConstructorStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
