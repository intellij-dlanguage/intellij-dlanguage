package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DLanguageAutoAssignmentImpl
import io.github.intellij.dlanguage.psi.named.DLanguageAutoAssignment
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DLanguageAutoAssignmentStub
import java.io.IOException

class AutoAssignmentStubElementType(debugName: String) :
    DNamedStubElementType<DLanguageAutoAssignmentStub, DLanguageAutoAssignment>(debugName) {
    override fun createPsi(stub: DLanguageAutoAssignmentStub): DLanguageAutoAssignment {
        return DLanguageAutoAssignmentImpl(stub, this)
    }

    override fun createStub(
        psi: DLanguageAutoAssignment,
        parentStub: StubElement<*>?
    ): DLanguageAutoAssignmentStub {
        return DLanguageAutoAssignmentStub(
            parentStub, this, psi.name,
            psi.attributes
        )
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageAutoAssignmentStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DLanguageAutoAssignmentStub {
        return DLanguageAutoAssignmentStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
