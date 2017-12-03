package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.stubs.DlangParameterStub
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DlangParameterStubElementType(debugName: String) : io.github.intellij.dlanguage.stubs.types.DNamedStubElementType<DlangParameterStub, io.github.intellij.dlanguage.psi.DLanguageParameter>(debugName) {

    override fun createPsi(stub: DlangParameterStub): io.github.intellij.dlanguage.psi.DLanguageParameter {
        return io.github.intellij.dlanguage.psi.impl.named.DLanguageParameterImpl(stub, this)
    }

    override fun createStub(psi: io.github.intellij.dlanguage.psi.DLanguageParameter, parentStub: StubElement<*>): DlangParameterStub {
        return DlangParameterStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangParameterStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DlangParameterStub {
        return DlangParameterStub(parentStub, this, dataStream.readName()!!,
            DAttributes.read(dataStream))
    }
}
