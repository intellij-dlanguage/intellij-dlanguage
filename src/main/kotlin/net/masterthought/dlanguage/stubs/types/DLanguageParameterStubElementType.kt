package net.masterthought.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import net.masterthought.dlanguage.psi.DLanguageParameter
import net.masterthought.dlanguage.psi.impl.named.DLanguageParameterImpl
import net.masterthought.dlanguage.stubs.DlangParameterStub
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DLanguageParameterStubElementType(debugName: String) : DNamedStubElementType<DlangParameterStub, DLanguageParameter>(debugName) {

    override fun createPsi(stub: DlangParameterStub): DLanguageParameter {
        return DLanguageParameterImpl(stub, this)
    }

    override fun createStub(psi: DLanguageParameter, parentStub: StubElement<*>): DlangParameterStub {
        return DlangParameterStub(parentStub, this, psi.name)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangParameterStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DlangParameterStub {
        return DlangParameterStub(parentStub, this, dataStream.readName()!!)
    }
}
