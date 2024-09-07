package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import io.github.intellij.dlanguage.psi.DLanguageStaticDestructor
import io.github.intellij.dlanguage.psi.impl.DLanguageStaticDestructorImpl
import io.github.intellij.dlanguage.stubs.DlangStaticDestructorStub
import java.io.IOException

class StaticDestructorStubElementType(debugName: String) :
    DStubElementType<DlangStaticDestructorStub, DLanguageStaticDestructor>(debugName) {
    override fun createPsi(stub: DlangStaticDestructorStub): DLanguageStaticDestructor {
        return DLanguageStaticDestructorImpl(stub, this)
    }

    override fun createStub(psi: DLanguageStaticDestructor, parentStub: StubElement<*>?): DlangStaticDestructorStub {
        return DlangStaticDestructorStub(parentStub, this)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DlangStaticDestructorStub {
        return DlangStaticDestructorStub(parentStub, this)
    }
}
