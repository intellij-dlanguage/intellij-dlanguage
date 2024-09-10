package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import io.github.intellij.dlanguage.psi.DLanguageStaticDestructor
import io.github.intellij.dlanguage.psi.impl.DLanguageStaticDestructorImpl
import io.github.intellij.dlanguage.stubs.DLanguageStaticDestructorStub
import java.io.IOException

class StaticDestructorStubElementType(debugName: String) :
    DStubElementType<DLanguageStaticDestructorStub, DLanguageStaticDestructor>(debugName) {
    override fun createPsi(stub: DLanguageStaticDestructorStub): DLanguageStaticDestructor {
        return DLanguageStaticDestructorImpl(stub, this)
    }

    override fun createStub(psi: DLanguageStaticDestructor, parentStub: StubElement<*>?): DLanguageStaticDestructorStub {
        return DLanguageStaticDestructorStub(parentStub, this)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DLanguageStaticDestructorStub {
        return DLanguageStaticDestructorStub(parentStub, this)
    }
}
