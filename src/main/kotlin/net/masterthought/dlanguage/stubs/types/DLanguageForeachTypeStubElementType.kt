package net.masterthought.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import net.masterthought.dlanguage.psi.DLanguageForeachType
import net.masterthought.dlanguage.psi.impl.DLanguageForeachTypeImpl
import net.masterthought.dlanguage.stubs.DLanguageForeachTypeStub
import net.masterthought.dlanguage.utils.DUtil
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DLanguageForeachTypeStubElementType(debugName: String) : DNamedStubElementType<DLanguageForeachTypeStub, DLanguageForeachType>(debugName) {

    override fun createPsi(stub: DLanguageForeachTypeStub): DLanguageForeachType {
        return DLanguageForeachTypeImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode?): Boolean {
        return DUtil.definitionNode(node!!)
    }

    override fun createStub(psi: DLanguageForeachType, parentStub: StubElement<*>): DLanguageForeachTypeStub {
        return DLanguageForeachTypeStub(parentStub, this, psi.name)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageForeachTypeStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageForeachTypeStub {
        return DLanguageForeachTypeStub(parentStub, this, dataStream.readName()!!)
    }
}
