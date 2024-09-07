package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.DLanguageNamedImportBind
import io.github.intellij.dlanguage.psi.impl.named.DLanguageNamedImportBindImpl
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DlangNamedImportBindStub
import java.io.IOException

class DLanguageNamedImportBindStubElementType(debugName: String) :
    DNamedStubElementType<DlangNamedImportBindStub, DLanguageNamedImportBind>(debugName) {
    override fun createPsi(stub: DlangNamedImportBindStub): DLanguageNamedImportBind {
        return DLanguageNamedImportBindImpl(stub, this)
    }

    override fun createStub(psi: DLanguageNamedImportBind, parentStub: StubElement<*>?): DlangNamedImportBindStub {
        return DlangNamedImportBindStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangNamedImportBindStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DlangNamedImportBindStub {
        return DlangNamedImportBindStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
