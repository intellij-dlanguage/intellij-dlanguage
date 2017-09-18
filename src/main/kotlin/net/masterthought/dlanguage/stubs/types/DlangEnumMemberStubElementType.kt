package net.masterthought.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import net.masterthought.dlanguage.psi.DLanguageEnumMember
import net.masterthought.dlanguage.psi.impl.named.DLanguageEnumMemberImpl
import net.masterthought.dlanguage.stubs.DlangEnumMemberStub
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DlangEnumMemberStubElementType(debugName: String) : DNamedStubElementType<DlangEnumMemberStub, DLanguageEnumMember>(debugName) {

    override fun createPsi(stub: DlangEnumMemberStub): DLanguageEnumMember {
        return DLanguageEnumMemberImpl(stub, this)
    }

    override fun createStub(psi: DLanguageEnumMember, parentStub: StubElement<*>): DlangEnumMemberStub {
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
