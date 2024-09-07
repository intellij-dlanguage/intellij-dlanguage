package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.psi.interfaces.DCompositeElement
import java.io.IOException

abstract class DStubElementType<S : StubElement<T>, T : DCompositeElement>(debugName: String) :
    IStubElementType<S, T>(debugName, DLanguage) {
    override fun indexStub(stub: S, sink: IndexSink) {
    }

    @Throws(IOException::class)
    override fun serialize(stub: S, dataStream: StubOutputStream) {
    }

    override fun getExternalId(): String {
        return "d." + super.toString()
    }
}

