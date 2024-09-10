package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import io.github.intellij.dlanguage.psi.DLanguageStaticConstructor
import io.github.intellij.dlanguage.psi.impl.DLanguageStaticConstructorImpl
import io.github.intellij.dlanguage.stubs.DLanguageStaticConstructorStub
import java.io.IOException

class StaticConstructorStubElementType(debugName: String) :
    DStubElementType<DLanguageStaticConstructorStub, DLanguageStaticConstructor>(debugName) {
    override fun createPsi(stub: DLanguageStaticConstructorStub): DLanguageStaticConstructor {
        return DLanguageStaticConstructorImpl(stub, this)
    }

    override fun createStub(psi: DLanguageStaticConstructor, parentStub: StubElement<*>?): DLanguageStaticConstructorStub {
        return DLanguageStaticConstructorStub(parentStub, this)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DLanguageStaticConstructorStub {
        return DLanguageStaticConstructorStub(parentStub, this)
    }
}
