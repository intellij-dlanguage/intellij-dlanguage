package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DLanguageConstructorImpl
import io.github.intellij.dlanguage.psi.named.DlangConstructor
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DlangConstructorStub
import java.io.IOException

class ConstructorStubElementType(debugName: String) :
    DNamedStubElementType<DlangConstructorStub, DlangConstructor>(debugName) {
    override fun createPsi(stub: DlangConstructorStub): DlangConstructor {
        return DLanguageConstructorImpl(stub, this)
    }

    override fun createStub(
        psi: DlangConstructor,
        parentStub: StubElement<*>?
    ): DlangConstructorStub {
        return DlangConstructorStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangConstructorStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DlangConstructorStub {
        return DlangConstructorStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
