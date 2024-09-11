package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.DLanguageIfCondition
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.stubs.DLanguageIfConditionStub
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DlangIfConditionStubElementType(debugName: String) : DNamedStubElementType<DLanguageIfConditionStub, DLanguageIfCondition>(debugName) {

    override fun createPsi(stub: DLanguageIfConditionStub): DLanguageIfCondition {
        return io.github.intellij.dlanguage.psi.impl.named.DLanguageIfConditionImpl(stub, this)
    }

    override fun createStub(psi: DLanguageIfCondition, parentStub: StubElement<*>): DLanguageIfConditionStub {
        return DLanguageIfConditionStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageIfConditionStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageIfConditionStub {
        return DLanguageIfConditionStub(parentStub, this, dataStream.readName(),
            DAttributes.read(dataStream))
    }
}
