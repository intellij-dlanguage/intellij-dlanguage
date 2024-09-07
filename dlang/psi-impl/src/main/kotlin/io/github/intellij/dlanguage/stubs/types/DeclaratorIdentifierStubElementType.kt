package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DlangDeclaratorIdentifierImpl
import io.github.intellij.dlanguage.psi.named.DlangDeclaratorIdentifier
import io.github.intellij.dlanguage.stubs.DlangDeclaratorIdentifierStub
import java.io.IOException

class DeclaratorIdentifierStubElementType(debugName: String) :
    DNamedStubElementType<DlangDeclaratorIdentifierStub, DlangDeclaratorIdentifier>(debugName) {
    override fun createPsi(stub: DlangDeclaratorIdentifierStub): DlangDeclaratorIdentifier {
        return DlangDeclaratorIdentifierImpl(stub, this)
    }

    override fun createStub(
        psi: DlangDeclaratorIdentifier,
        parentStub: StubElement<out PsiElement?>?
    ): DlangDeclaratorIdentifierStub {
        return DlangDeclaratorIdentifierStub(parentStub, this, psi.name, null)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangDeclaratorIdentifierStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DlangDeclaratorIdentifierStub {
        return DlangDeclaratorIdentifierStub(parentStub, this, dataStream.readName(), null)
    }
}
