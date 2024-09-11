package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import io.github.intellij.dlanguage.psi.DLanguageSharedStaticDestructor
import io.github.intellij.dlanguage.psi.impl.DLanguageSharedStaticDestructorImpl
import io.github.intellij.dlanguage.stubs.DLanguageSharedStaticDestructorStub
import java.io.IOException

class SharedStaticDestructorStubElementType(debugName: String) :
    DStubElementType<DLanguageSharedStaticDestructorStub, DLanguageSharedStaticDestructor>(debugName) {
    override fun createPsi(stub: DLanguageSharedStaticDestructorStub): DLanguageSharedStaticDestructor {
        return DLanguageSharedStaticDestructorImpl(stub, this)
    }

    override fun createStub(
        psi: DLanguageSharedStaticDestructor,
        parentStub: StubElement<*>?
    ): DLanguageSharedStaticDestructorStub {
        return DLanguageSharedStaticDestructorStub(parentStub, this)
    }

    @Throws(IOException::class)
    override fun deserialize(
        dataStream: StubInputStream,
        parentStub: StubElement<*>?
    ): DLanguageSharedStaticDestructorStub {
        return DLanguageSharedStaticDestructorStub(parentStub, this)
    }
}
