package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.DLanguageIfCondition
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.stubs.DlangIfConditionStub
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DlangIfConditionStubElementType(debugName: String) : DNamedStubElementType<DlangIfConditionStub, DLanguageIfCondition>(debugName) {

    override fun createPsi(stub: DlangIfConditionStub): DLanguageIfCondition {
        return io.github.intellij.dlanguage.psi.impl.named.DLanguageIfConditionImpl(stub, this)
    }

    override fun createStub(psi: DLanguageIfCondition, parentStub: StubElement<*>): DlangIfConditionStub {
        return DlangIfConditionStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangIfConditionStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DlangIfConditionStub {
        return DlangIfConditionStub(parentStub, this, dataStream.readName(),
            DAttributes.read(dataStream))
    }
}
