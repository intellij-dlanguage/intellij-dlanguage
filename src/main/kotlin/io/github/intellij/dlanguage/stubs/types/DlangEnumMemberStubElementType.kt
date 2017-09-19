package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.DLanguageEnumMember
import io.github.intellij.dlanguage.psi.impl.named.DLanguageEnumMemberImpl
import io.github.intellij.dlanguage.stubs.DlangEnumMemberStub
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DlangEnumMemberStubElementType(debugName: String) : io.github.intellij.dlanguage.stubs.types.DNamedStubElementType<DlangEnumMemberStub, io.github.intellij.dlanguage.psi.DLanguageEnumMember>(debugName) {

    override fun createPsi(stub: DlangEnumMemberStub): io.github.intellij.dlanguage.psi.DLanguageEnumMember {
        return io.github.intellij.dlanguage.psi.impl.named.DLanguageEnumMemberImpl(stub, this)
    }

    override fun createStub(psi: io.github.intellij.dlanguage.psi.DLanguageEnumMember, parentStub: StubElement<*>): DlangEnumMemberStub {
        return DlangEnumMemberStub(parentStub, this, psi.name)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangEnumMemberStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DlangEnumMemberStub {
        return DlangEnumMemberStub(parentStub, this, dataStream.readName()!!)
    }
}
