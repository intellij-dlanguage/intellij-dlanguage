package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DLanguageAliasInitializerImpl
import io.github.intellij.dlanguage.psi.named.DLanguageAliasInitializer
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DLanguageAliasInitializerStub
import java.io.IOException

class AliasInitializerStubElementType(debugName: String) :
    DNamedStubElementType<DLanguageAliasInitializerStub, DLanguageAliasInitializer>(debugName) {
    override fun createPsi(stub: DLanguageAliasInitializerStub): DLanguageAliasInitializer {
        return DLanguageAliasInitializerImpl(stub, this)
    }

    override fun createStub(
        psi: DLanguageAliasInitializer,
        parentStub: StubElement<*>
    ): DLanguageAliasInitializerStub {
        return DLanguageAliasInitializerStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageAliasInitializerStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageAliasInitializerStub {
        return DLanguageAliasInitializerStub(
            parentStub, this, dataStream.readName(),
            read(dataStream)
        )
    }
}
