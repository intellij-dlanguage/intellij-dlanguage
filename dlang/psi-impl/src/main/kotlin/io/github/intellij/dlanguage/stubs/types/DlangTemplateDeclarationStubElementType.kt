package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DlangTemplateDeclarationImpl
import io.github.intellij.dlanguage.psi.named.DlangTemplateDeclaration
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DlangTemplateDeclarationStub
import java.io.IOException

class DlangTemplateDeclarationStubElementType(debugName: String) :
    DNamedStubElementType<DlangTemplateDeclarationStub, DlangTemplateDeclaration>(debugName) {
    override fun createPsi(stub: DlangTemplateDeclarationStub): DlangTemplateDeclaration {
        return DlangTemplateDeclarationImpl(stub, this)
    }

    override fun createStub(psi: DlangTemplateDeclaration, parentStub: StubElement<*>?): DlangTemplateDeclarationStub {
        return DlangTemplateDeclarationStub(
            parentStub, this, psi.name,
            psi.attributes
        )
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangTemplateDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DlangTemplateDeclarationStub {
        return DlangTemplateDeclarationStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
