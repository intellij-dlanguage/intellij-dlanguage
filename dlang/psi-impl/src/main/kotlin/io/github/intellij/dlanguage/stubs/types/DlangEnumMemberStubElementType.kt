package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DLanguageEnumMemberImpl
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.stubs.DLanguageEnumMemberStub
import io.github.intellij.dlanguage.utils.EnumMember
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DlangEnumMemberStubElementType(debugName: String) : DNamedStubElementType<DLanguageEnumMemberStub, EnumMember>(debugName) {

    override fun createPsi(stub: DLanguageEnumMemberStub): EnumMember {
        return DLanguageEnumMemberImpl(stub, this)
    }

    override fun createStub(psi: EnumMember, parentStub: StubElement<*>): DLanguageEnumMemberStub {
        return DLanguageEnumMemberStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageEnumMemberStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageEnumMemberStub {
        return DLanguageEnumMemberStub(parentStub, this, dataStream.readName(),
            DAttributes.read(dataStream))
    }
}
