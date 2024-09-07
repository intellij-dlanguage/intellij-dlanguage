package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DLanguageAliasInitializerImpl
import io.github.intellij.dlanguage.psi.named.DlangAliasInitializer
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DlangAliasInitializerStub
import java.io.IOException

class AliasInitializerStubElementType(debugName: String) :
    DNamedStubElementType<DlangAliasInitializerStub, DlangAliasInitializer>(debugName) {
    override fun createPsi(stub: DlangAliasInitializerStub): DlangAliasInitializer {
        return DLanguageAliasInitializerImpl(stub, this)
    }

    override fun createStub(
        psi: DlangAliasInitializer,
        parentStub: StubElement<*>
    ): DlangAliasInitializerStub {
        return DlangAliasInitializerStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangAliasInitializerStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DlangAliasInitializerStub {
        return DlangAliasInitializerStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
