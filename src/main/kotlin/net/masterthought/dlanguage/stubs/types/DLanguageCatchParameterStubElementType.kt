package net.masterthought.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import net.masterthought.dlanguage.psi.DLanguageCatchParameter
import net.masterthought.dlanguage.psi.impl.DLanguageCatchParameterImpl
import net.masterthought.dlanguage.stubs.DLanguageCatchParameterStub
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */

class DLanguageCatchParameterStubElementType(debugName: String) : DNamedStubElementType<DLanguageCatchParameterStub, DLanguageCatchParameter>(debugName) {
    override fun createPsi(stub: DLanguageCatchParameterStub): DLanguageCatchParameter {
        return DLanguageCatchParameterImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode?): Boolean {
        return true
    }

    override fun createStub(psi: DLanguageCatchParameter, parentStub: StubElement<*>): DLanguageCatchParameterStub {
        return DLanguageCatchParameterStub(parentStub, this, psi.name)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageCatchParameterStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageCatchParameterStub {
        return DLanguageCatchParameterStub(parentStub, this, dataStream.readName()!!)
    }

}
