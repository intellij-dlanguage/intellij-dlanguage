package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.impl.named.DlangVersionSpecificationImpl
import io.github.intellij.dlanguage.psi.named.DLanguageVersionSpecification
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DLanguageVersionSpecificationStub
import java.io.IOException

/**
 * Created by francis on 1/5/2018.
 */
class VersionSpecificationElementType(debugName: String) :
    DNamedStubElementType<DLanguageVersionSpecificationStub, DLanguageVersionSpecification>(debugName) {
    override fun createPsi(stub: DLanguageVersionSpecificationStub): DLanguageVersionSpecification {
        return DlangVersionSpecificationImpl(stub, this)
    }

    override fun createStub(
        psi: DLanguageVersionSpecification,
        parentStub: StubElement<*>?
    ): DLanguageVersionSpecificationStub {
        return DLanguageVersionSpecificationStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun deserialize(
        dataStream: StubInputStream,
        parentStub: StubElement<*>?
    ): DLanguageVersionSpecificationStub {
        val name = dataStream.readName()
        val attributes = read(dataStream)
        return DLanguageVersionSpecificationStub(parentStub, this, name?.getString(), attributes)
    }

    @Throws(IOException::class)
    override fun serialize(
        stub: DLanguageVersionSpecificationStub,
        dataStream: StubOutputStream
    ) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }
}
