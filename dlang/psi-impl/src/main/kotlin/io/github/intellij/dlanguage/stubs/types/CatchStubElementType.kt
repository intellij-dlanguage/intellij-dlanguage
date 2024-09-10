package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.stubs.DLanguageCatchStub
import io.github.intellij.dlanguage.utils.Catch
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */

class CatchStubElementType(debugName: String) : DNamedStubElementType<DLanguageCatchStub, Catch>(debugName) {
    override fun createPsi(stub: DLanguageCatchStub): Catch {
        return io.github.intellij.dlanguage.psi.impl.named.DLanguageCatchImpl(stub, this)
    }

    override fun createStub(psi: Catch, parentStub: StubElement<*>): DLanguageCatchStub {
        return DLanguageCatchStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageCatchStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageCatchStub {
        return DLanguageCatchStub(
            parentStub, this, dataStream.readName(),
            DAttributes.read(dataStream)
        )
    }

}
