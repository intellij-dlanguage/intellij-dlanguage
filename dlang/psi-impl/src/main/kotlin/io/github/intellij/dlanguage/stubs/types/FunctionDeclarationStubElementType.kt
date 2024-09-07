package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DLanguageFunctionDeclarationImpl
import io.github.intellij.dlanguage.psi.named.DlangFunctionDeclaration
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DlangFunctionDeclarationStub
import java.io.IOException

class FunctionDeclarationStubElementType(debugName: String) :
    DNamedStubElementType<DlangFunctionDeclarationStub, DlangFunctionDeclaration>(debugName) {
    override fun createPsi(stub: DlangFunctionDeclarationStub): DlangFunctionDeclaration {
        return DLanguageFunctionDeclarationImpl(stub, this)
    }

    override fun createStub(
        psi: DlangFunctionDeclaration,
        parentStub: StubElement<*>?
    ): DlangFunctionDeclarationStub {
        return DlangFunctionDeclarationStub(
            parentStub, this, psi.name,
            psi.attributes
        )
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangFunctionDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DlangFunctionDeclarationStub {
        return DlangFunctionDeclarationStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
