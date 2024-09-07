package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import io.github.intellij.dlanguage.psi.impl.DLanguageDestructorImpl
import io.github.intellij.dlanguage.psi.named.DlangDestructor
import io.github.intellij.dlanguage.stubs.DlangDestructorStub
import java.io.IOException

class DestructorStubElementType(debugName: String) : DStubElementType<DlangDestructorStub, DlangDestructor>(debugName) {
    override fun createPsi(stub: DlangDestructorStub): DlangDestructor {
        return DLanguageDestructorImpl(stub, this)
    }

    override fun createStub(
        psi: DlangDestructor,
        parentStub: StubElement<*>?
    ): DlangDestructorStub {
        return DlangDestructorStub(parentStub, this)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DlangDestructorStub {
        return DlangDestructorStub(parentStub, this)
    }
}
