package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.stubs.DLanguageForeachTypeStub
import io.github.intellij.dlanguage.utils.ForeachType
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DlangForeachTypeStubElementType(debugName: String) : DNamedStubElementType<DLanguageForeachTypeStub, ForeachType>(debugName) {

    override fun createPsi(stub: DLanguageForeachTypeStub): ForeachType {
        return io.github.intellij.dlanguage.psi.impl.named.DLanguageForeachTypeImpl(stub, this)
    }

    override fun createStub(psi: ForeachType, parentStub: StubElement<*>): DLanguageForeachTypeStub {
        return DLanguageForeachTypeStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageForeachTypeStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageForeachTypeStub {
        return DLanguageForeachTypeStub(parentStub, this, dataStream.readName(),
            DAttributes.read(dataStream))
    }
}
