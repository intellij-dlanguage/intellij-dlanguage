package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.DLanguageLabeledStatement
import io.github.intellij.dlanguage.psi.impl.named.DLanguageLabeledStatementImpl
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DLanguageLabeledStatementStub
import java.io.IOException

class LabeledStatementStubElementType(debugName: String) :
    DNamedStubElementType<DLanguageLabeledStatementStub, DLanguageLabeledStatement>(debugName) {
    override fun createPsi(stub: DLanguageLabeledStatementStub): DLanguageLabeledStatement {
        return DLanguageLabeledStatementImpl(stub, this)
    }

    override fun createStub(psi: DLanguageLabeledStatement, parentStub: StubElement<*>?): DLanguageLabeledStatementStub {
        return DLanguageLabeledStatementStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageLabeledStatementStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DLanguageLabeledStatementStub {
        return DLanguageLabeledStatementStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
