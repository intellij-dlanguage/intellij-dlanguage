package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DlangModuleDeclarationImpl
import io.github.intellij.dlanguage.psi.named.DLanguageModuleDeclaration
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DLanguageModuleDeclarationStub
import java.io.IOException

class ModuleDeclarationStubElementType(debugName: String) :
    DNamedStubElementType<DLanguageModuleDeclarationStub, DLanguageModuleDeclaration>(debugName) {
    override fun createPsi(stub: DLanguageModuleDeclarationStub): DLanguageModuleDeclaration {
        return DlangModuleDeclarationImpl(stub, this)
    }

    override fun createStub(psi: DLanguageModuleDeclaration, parentStub: StubElement<*>?): DLanguageModuleDeclarationStub {
        return DLanguageModuleDeclarationStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageModuleDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DLanguageModuleDeclarationStub {
        return DLanguageModuleDeclarationStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
