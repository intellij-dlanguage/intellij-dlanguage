package net.masterthought.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import net.masterthought.dlanguage.psi.DLanguageCatch
import net.masterthought.dlanguage.psi.impl.named.DLanguageCatchImpl
import net.masterthought.dlanguage.stubs.DlangCatchStub
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */

class CatchStubElementType(debugName: String) : DNamedStubElementType<DlangCatchStub, DLanguageCatch>(debugName) {
    override fun createPsi(stub: DlangCatchStub): DLanguageCatch {
        return DLanguageCatchImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode?): Boolean {
        return true
    }

    override fun createStub(psi: DLanguageCatch, parentStub: StubElement<*>): DlangCatchStub {
        return DlangCatchStub(parentStub, this, psi.name)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangCatchStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DlangCatchStub {
        return DlangCatchStub(parentStub, this, dataStream.readName()!!)
    }

}
