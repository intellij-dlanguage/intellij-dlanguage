package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import io.github.intellij.dlanguage.psi.DLanguageUnittest
import io.github.intellij.dlanguage.psi.impl.DlangUnittestImpl
import io.github.intellij.dlanguage.stubs.UnittestStubImpl
import io.github.intellij.dlanguage.stubs.interfaces.DlangUnittestStub
import java.io.IOException

/**
 * Created by francis on 4/8/2017.
 */
class UnittestStubElementType(debugName: String) : DStubElementType<DlangUnittestStub, DLanguageUnittest>(debugName) {
    override fun createPsi(stub: DlangUnittestStub): DLanguageUnittest {
        return DlangUnittestImpl(stub, this)
    }

    override fun createStub(psi: DLanguageUnittest, parentStub: StubElement<*>?): DlangUnittestStub {
        return UnittestStubImpl(parentStub, this)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DlangUnittestStub {
        return UnittestStubImpl(parentStub, this)
    }
}
