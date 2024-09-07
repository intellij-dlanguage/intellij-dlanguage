package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DlangVersionSpecificationImpl
import io.github.intellij.dlanguage.psi.named.DlangVersionSpecification
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.VersionSpecificationStub
import java.io.IOException

/**
 * Created by francis on 1/5/2018.
 */
class VersionSpecificationElementType(debugName: String) :
    DNamedStubElementType<VersionSpecificationStub, DlangVersionSpecification>(debugName) {
    override fun createPsi(stub: VersionSpecificationStub): DlangVersionSpecification {
        return DlangVersionSpecificationImpl(stub, this)
    }

    override fun createStub(
        psi: DlangVersionSpecification,
        parentStub: StubElement<*>?
    ): VersionSpecificationStub {
        return VersionSpecificationStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun deserialize(
        dataStream: StubInputStream,
        parentStub: StubElement<*>?
    ): VersionSpecificationStub {
        val name = dataStream.readName()
        val attributes = read(dataStream)
        return VersionSpecificationStub(parentStub, this, name?.getString(), attributes)
    }

    @Throws(IOException::class)
    override fun serialize(
        stub: VersionSpecificationStub,
        dataStream: StubOutputStream
    ) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }
}
