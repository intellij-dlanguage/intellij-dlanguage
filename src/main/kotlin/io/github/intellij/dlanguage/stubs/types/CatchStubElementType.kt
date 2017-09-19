package io.github.intellij.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.DLanguageCatch
import io.github.intellij.dlanguage.psi.impl.named.DLanguageCatchImpl
import io.github.intellij.dlanguage.stubs.DlangCatchStub
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */

class CatchStubElementType(debugName: String) : io.github.intellij.dlanguage.stubs.types.DNamedStubElementType<DlangCatchStub, io.github.intellij.dlanguage.psi.DLanguageCatch>(debugName) {
    override fun createPsi(stub: DlangCatchStub): io.github.intellij.dlanguage.psi.DLanguageCatch {
        return io.github.intellij.dlanguage.psi.impl.named.DLanguageCatchImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode?): Boolean {
        return true
    }

    override fun createStub(psi: io.github.intellij.dlanguage.psi.DLanguageCatch, parentStub: StubElement<*>): DlangCatchStub {
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
