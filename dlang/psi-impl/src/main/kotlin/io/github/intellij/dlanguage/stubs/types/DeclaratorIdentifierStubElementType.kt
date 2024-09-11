package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DlangDeclaratorIdentifierImpl
import io.github.intellij.dlanguage.psi.named.DLanguageDeclaratorIdentifier
import io.github.intellij.dlanguage.stubs.DLanguageDeclaratorIdentifierStub
import java.io.IOException

class DeclaratorIdentifierStubElementType(debugName: String) :
    DNamedStubElementType<DLanguageDeclaratorIdentifierStub, DLanguageDeclaratorIdentifier>(debugName) {
    override fun createPsi(stub: DLanguageDeclaratorIdentifierStub): DLanguageDeclaratorIdentifier {
        return DlangDeclaratorIdentifierImpl(stub, this)
    }

    override fun createStub(
        psi: DLanguageDeclaratorIdentifier,
        parentStub: StubElement<out PsiElement?>?
    ): DLanguageDeclaratorIdentifierStub {
        return DLanguageDeclaratorIdentifierStub(parentStub, this, psi.name, null)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageDeclaratorIdentifierStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DLanguageDeclaratorIdentifierStub {
        return DLanguageDeclaratorIdentifierStub(parentStub, this, dataStream.readName(), null)
    }
}
