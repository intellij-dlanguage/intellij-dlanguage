package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DlangEnumDeclarationImpl
import io.github.intellij.dlanguage.psi.named.DlangEnumDeclaration
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DlangEnumDeclarationStub
import java.io.IOException

/**
 * Created by francis on 3/9/2017.
 */
class EnumDeclarationStubElementType(debugName: String) :
    DNamedStubElementType<DlangEnumDeclarationStub, DlangEnumDeclaration>(debugName) {
    override fun createPsi(stub: DlangEnumDeclarationStub): DlangEnumDeclaration {
        return DlangEnumDeclarationImpl(stub, this)
    }

    override fun createStub(psi: DlangEnumDeclaration, parentStub: StubElement<*>?): DlangEnumDeclarationStub {
        return DlangEnumDeclarationStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangEnumDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DlangEnumDeclarationStub {
        return DlangEnumDeclarationStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
