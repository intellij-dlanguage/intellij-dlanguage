package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DlangTemplateDeclarationImpl
import io.github.intellij.dlanguage.psi.named.DLanguageTemplateDeclaration
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DLanguageTemplateDeclarationStub
import java.io.IOException

class DlangTemplateDeclarationStubElementType(debugName: String) :
    DNamedStubElementType<DLanguageTemplateDeclarationStub, DLanguageTemplateDeclaration>(debugName) {
    override fun createPsi(stub: DLanguageTemplateDeclarationStub): DLanguageTemplateDeclaration {
        return DlangTemplateDeclarationImpl(stub, this)
    }

    override fun createStub(psi: DLanguageTemplateDeclaration, parentStub: StubElement<*>?): DLanguageTemplateDeclarationStub {
        return DLanguageTemplateDeclarationStub(
            parentStub, this, psi.name,
            psi.attributes
        )
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageTemplateDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DLanguageTemplateDeclarationStub {
        return DLanguageTemplateDeclarationStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
