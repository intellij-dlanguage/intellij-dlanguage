package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DlangUnionDeclarationImpl
import io.github.intellij.dlanguage.psi.named.DLanguageUnionDeclaration
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.stubs.DlangUnionDeclarationStub
import java.io.IOException

class DlangUnionDeclarationStubElementType(debugName: String) : DNamedStubElementType<DlangUnionDeclarationStub, DLanguageUnionDeclaration>(debugName) {

    override fun createPsi(stub: DlangUnionDeclarationStub): DLanguageUnionDeclaration {
        return DlangUnionDeclarationImpl(stub, this)
    }

    override fun createStub(psi: DLanguageUnionDeclaration, parentStub: StubElement<*>): DlangUnionDeclarationStub {
        return DlangUnionDeclarationStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangUnionDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DlangUnionDeclarationStub {
        return DlangUnionDeclarationStub(
            parentStub, this, dataStream.readName(),
            DAttributes.read(dataStream)
        )
    }
}
