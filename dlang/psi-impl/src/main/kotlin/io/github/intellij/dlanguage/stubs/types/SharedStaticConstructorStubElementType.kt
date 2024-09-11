package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import io.github.intellij.dlanguage.psi.DLanguageSharedStaticConstructor
import io.github.intellij.dlanguage.psi.impl.DLanguageSharedStaticConstructorImpl
import io.github.intellij.dlanguage.stubs.DLanguageSharedStaticConstructorStub
import java.io.IOException

class SharedStaticConstructorStubElementType(debugName: String) :
    DStubElementType<DLanguageSharedStaticConstructorStub, DLanguageSharedStaticConstructor>(debugName) {
    override fun createPsi(stub: DLanguageSharedStaticConstructorStub): DLanguageSharedStaticConstructor {
        return DLanguageSharedStaticConstructorImpl(stub, this)
    }

    override fun createStub(
        psi: DLanguageSharedStaticConstructor,
        parentStub: StubElement<*>?
    ): DLanguageSharedStaticConstructorStub {
        return DLanguageSharedStaticConstructorStub(parentStub, this)
    }

    @Throws(IOException::class)
    override fun deserialize(
        dataStream: StubInputStream,
        parentStub: StubElement<*>?
    ): DLanguageSharedStaticConstructorStub {
        return DLanguageSharedStaticConstructorStub(parentStub, this)
    }
}
