package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import io.github.intellij.dlanguage.psi.DLanguageStaticConstructor
import io.github.intellij.dlanguage.psi.impl.DLanguageStaticConstructorImpl
import io.github.intellij.dlanguage.stubs.DlangStaticConstructorStub
import java.io.IOException

class StaticConstructorStubElementType(debugName: String) :
    DStubElementType<DlangStaticConstructorStub, DLanguageStaticConstructor>(debugName) {
    override fun createPsi(stub: DlangStaticConstructorStub): DLanguageStaticConstructor {
        return DLanguageStaticConstructorImpl(stub, this)
    }

    override fun createStub(psi: DLanguageStaticConstructor, parentStub: StubElement<*>?): DlangStaticConstructorStub {
        return DlangStaticConstructorStub(parentStub, this)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DlangStaticConstructorStub {
        return DlangStaticConstructorStub(parentStub, this)
    }
}
