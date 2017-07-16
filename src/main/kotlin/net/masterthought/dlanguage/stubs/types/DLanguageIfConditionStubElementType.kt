package net.masterthought.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import net.masterthought.dlanguage.psi.DLanguageIfCondition
import net.masterthought.dlanguage.psi.impl.named.DLanguageIfConditionImpl
import net.masterthought.dlanguage.stubs.DLanguageIfConditionStub
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DLanguageIfConditionStubElementType(debugName: String) : DNamedStubElementType<DLanguageIfConditionStub, DLanguageIfCondition>(debugName) {

    override fun createPsi(stub: DLanguageIfConditionStub):DLanguageIfCondition{
        return DLanguageIfConditionImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode?): Boolean {
        return true
    }

    override fun createStub(psi: DLanguageIfCondition, parentStub: StubElement<*>): DLanguageIfConditionStub {
        return DLanguageIfConditionStub(parentStub, this, psi.name)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageIfConditionStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageIfConditionStub {
        return DLanguageIfConditionStub(parentStub, this, dataStream.readName()!!)
    }
}
