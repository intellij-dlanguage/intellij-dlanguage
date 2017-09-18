package net.masterthought.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import net.masterthought.dlanguage.psi.DLanguageForeachType
import net.masterthought.dlanguage.psi.impl.named.DLanguageForeachTypeImpl
import net.masterthought.dlanguage.stubs.DlangForeachTypeStub
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DlangForeachTypeStubElementType(debugName: String) : DNamedStubElementType<DlangForeachTypeStub, DLanguageForeachType>(debugName) {

    override fun createPsi(stub: DlangForeachTypeStub): DLanguageForeachType {
        return DLanguageForeachTypeImpl(stub, this)
    }

    override fun createStub(psi: DLanguageForeachType, parentStub: StubElement<*>): DlangForeachTypeStub {
        return DlangForeachTypeStub(parentStub, this, psi.name)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangForeachTypeStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DlangForeachTypeStub {
        return DlangForeachTypeStub(parentStub, this, dataStream.readName()!!)
    }
}
