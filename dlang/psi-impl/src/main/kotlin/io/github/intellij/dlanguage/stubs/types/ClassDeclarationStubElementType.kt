package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DlangClassDeclarationImpl
import io.github.intellij.dlanguage.psi.named.DLanguageClassDeclaration
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DLanguageClassDeclarationStub
import java.io.IOException

class ClassDeclarationStubElementType(debugName: String) :
    DNamedStubElementType<DLanguageClassDeclarationStub, DLanguageClassDeclaration>(debugName) {
    override fun createPsi(stub: DLanguageClassDeclarationStub): DLanguageClassDeclaration {
        return DlangClassDeclarationImpl(stub, this)
    }

    override fun createStub(psi: DLanguageClassDeclaration, parentStub: StubElement<*>?): DLanguageClassDeclarationStub {
        return DLanguageClassDeclarationStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageClassDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DLanguageClassDeclarationStub {
        return DLanguageClassDeclarationStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
