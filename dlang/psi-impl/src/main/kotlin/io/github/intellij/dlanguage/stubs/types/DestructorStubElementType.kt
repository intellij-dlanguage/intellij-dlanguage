package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import io.github.intellij.dlanguage.psi.impl.DLanguageDestructorImpl
import io.github.intellij.dlanguage.psi.named.DLanguageDestructor
import io.github.intellij.dlanguage.stubs.DLanguageDestructorStub
import java.io.IOException

class DestructorStubElementType(debugName: String) : DStubElementType<DLanguageDestructorStub, DLanguageDestructor>(debugName) {
    override fun createPsi(stub: DLanguageDestructorStub): DLanguageDestructor {
        return DLanguageDestructorImpl(stub, this)
    }

    override fun createStub(
        psi: DLanguageDestructor,
        parentStub: StubElement<*>?
    ): DLanguageDestructorStub {
        return DLanguageDestructorStub(parentStub, this)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DLanguageDestructorStub {
        return DLanguageDestructorStub(parentStub, this)
    }
}
