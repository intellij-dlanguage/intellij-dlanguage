package net.masterthought.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import net.masterthought.dlanguage.psi.DLanguageConditionVariableDeclaration
import net.masterthought.dlanguage.psi.impl.DLanguageConditionVariableDeclarationImpl
import net.masterthought.dlanguage.stubs.DLanguageConditionVariableDeclarationStub
import net.masterthought.dlanguage.utils.DUtil
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DLanguageConditionVariableDeclarationStubElementType(debugName: String) : DNamedStubElementType<DLanguageConditionVariableDeclarationStub, DLanguageConditionVariableDeclaration>(debugName) {

    override fun createPsi(stub: DLanguageConditionVariableDeclarationStub): DLanguageConditionVariableDeclaration {
        return DLanguageConditionVariableDeclarationImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode?): Boolean {
        return DUtil.definitionNode(node!!)
    }

    override fun createStub(psi: DLanguageConditionVariableDeclaration, parentStub: StubElement<*>): DLanguageConditionVariableDeclarationStub {
        return DLanguageConditionVariableDeclarationStub(parentStub, this, psi.name)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageConditionVariableDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageConditionVariableDeclarationStub {
        return DLanguageConditionVariableDeclarationStub(parentStub, this, dataStream.readName()!!)
    }
}
