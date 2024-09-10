package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.named.DLanguageParameter
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.stubs.DLanguageParameterStub
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DlangParameterStubElementType(debugName: String) : DNamedStubElementType<DLanguageParameterStub, DLanguageParameter>(debugName) {

    override fun createPsi(stub: DLanguageParameterStub): DLanguageParameter {
        return io.github.intellij.dlanguage.psi.impl.named.DLanguageParameterImpl(stub, this)
    }

    override fun createStub(psi: DLanguageParameter, parentStub: StubElement<*>): DLanguageParameterStub {
        return DLanguageParameterStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageParameterStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageParameterStub {
        return DLanguageParameterStub(parentStub, this, dataStream.readName(),
            DAttributes.read(dataStream))
    }
}
