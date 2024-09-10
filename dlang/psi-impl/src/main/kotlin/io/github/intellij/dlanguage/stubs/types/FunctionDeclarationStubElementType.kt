package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DLanguageFunctionDeclarationImpl
import io.github.intellij.dlanguage.psi.named.DLanguageFunctionDeclaration
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DLanguageFunctionDeclarationStub
import java.io.IOException

class FunctionDeclarationStubElementType(debugName: String) :
    DNamedStubElementType<DLanguageFunctionDeclarationStub, DLanguageFunctionDeclaration>(debugName) {
    override fun createPsi(stub: DLanguageFunctionDeclarationStub): DLanguageFunctionDeclaration {
        return DLanguageFunctionDeclarationImpl(stub, this)
    }

    override fun createStub(
        psi: DLanguageFunctionDeclaration,
        parentStub: StubElement<*>?
    ): DLanguageFunctionDeclarationStub {
        return DLanguageFunctionDeclarationStub(
            parentStub, this, psi.name,
            psi.attributes
        )
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageFunctionDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DLanguageFunctionDeclarationStub {
        return DLanguageFunctionDeclarationStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
