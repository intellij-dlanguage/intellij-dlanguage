package net.masterthought.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import net.masterthought.dlanguage.psi.DLanguageParameter
import net.masterthought.dlanguage.psi.impl.DLanguageParameterImpl
import net.masterthought.dlanguage.stubs.DLanguageParameterStub
import net.masterthought.dlanguage.utils.DUtil
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DLanguageParameterStubElementType(debugName: String) : DNamedStubElementType<DLanguageParameterStub, DLanguageParameter>(debugName) {

    override fun createPsi(stub: DLanguageParameterStub): DLanguageParameter {
        return DLanguageParameterImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode?): Boolean {
        return DUtil.definitionNode(node!!)
    }

    override fun createStub(psi: DLanguageParameter, parentStub: StubElement<*>): DLanguageParameterStub {
        return DLanguageParameterStub(parentStub, this, psi.name)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageParameterStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageParameterStub {
        return DLanguageParameterStub(parentStub, this, dataStream.readName()!!)
    }
}
