package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import io.github.intellij.dlanguage.psi.DLanguageSharedStaticDestructor
import io.github.intellij.dlanguage.psi.impl.DLanguageSharedStaticDestructorImpl
import io.github.intellij.dlanguage.stubs.DlangSharedStaticDestructorStub
import java.io.IOException

class SharedStaticDestructorStubElementType(debugName: String) :
    DStubElementType<DlangSharedStaticDestructorStub, DLanguageSharedStaticDestructor>(debugName) {
    override fun createPsi(stub: DlangSharedStaticDestructorStub): DLanguageSharedStaticDestructor {
        return DLanguageSharedStaticDestructorImpl(stub, this)
    }

    override fun createStub(
        psi: DLanguageSharedStaticDestructor,
        parentStub: StubElement<*>?
    ): DlangSharedStaticDestructorStub {
        return DlangSharedStaticDestructorStub(parentStub, this)
    }

    @Throws(IOException::class)
    override fun deserialize(
        dataStream: StubInputStream,
        parentStub: StubElement<*>?
    ): DlangSharedStaticDestructorStub {
        return DlangSharedStaticDestructorStub(parentStub, this)
    }
}
