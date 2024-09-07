package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import io.github.intellij.dlanguage.psi.DLanguageSharedStaticConstructor
import io.github.intellij.dlanguage.psi.impl.DLanguageSharedStaticConstructorImpl
import io.github.intellij.dlanguage.stubs.DlangSharedStaticConstructorStub
import java.io.IOException

class SharedStaticConstructorStubElementType(debugName: String) :
    DStubElementType<DlangSharedStaticConstructorStub, DLanguageSharedStaticConstructor>(debugName) {
    override fun createPsi(stub: DlangSharedStaticConstructorStub): DLanguageSharedStaticConstructor {
        return DLanguageSharedStaticConstructorImpl(stub, this)
    }

    override fun createStub(
        psi: DLanguageSharedStaticConstructor,
        parentStub: StubElement<*>?
    ): DlangSharedStaticConstructorStub {
        return DlangSharedStaticConstructorStub(parentStub, this)
    }

    @Throws(IOException::class)
    override fun deserialize(
        dataStream: StubInputStream,
        parentStub: StubElement<*>?
    ): DlangSharedStaticConstructorStub {
        return DlangSharedStaticConstructorStub(parentStub, this)
    }
}
