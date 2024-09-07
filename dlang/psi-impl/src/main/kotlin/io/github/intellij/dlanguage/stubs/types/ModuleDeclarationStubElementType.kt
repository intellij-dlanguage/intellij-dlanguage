package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DlangModuleDeclarationImpl
import io.github.intellij.dlanguage.psi.named.DlangModuleDeclaration
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DlangModuleDeclarationStub
import java.io.IOException

class ModuleDeclarationStubElementType(debugName: String) :
    DNamedStubElementType<DlangModuleDeclarationStub, DlangModuleDeclaration>(debugName) {
    override fun createPsi(stub: DlangModuleDeclarationStub): DlangModuleDeclaration {
        return DlangModuleDeclarationImpl(stub, this)
    }

    override fun createStub(psi: DlangModuleDeclaration, parentStub: StubElement<*>?): DlangModuleDeclarationStub {
        return DlangModuleDeclarationStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangModuleDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DlangModuleDeclarationStub {
        return DlangModuleDeclarationStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
