package net.masterthought.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import net.masterthought.dlanguage.psi.DLanguageAliasDeclarationY
import net.masterthought.dlanguage.psi.impl.DLanguageAliasDeclarationYImpl
import net.masterthought.dlanguage.stubs.DLanguageAliasDeclarationYStub
import net.masterthought.dlanguage.utils.DUtil
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */

class DLanguageAliasDeclarationYStubElementType(debugName: String) : DNamedStubElementType<DLanguageAliasDeclarationYStub, DLanguageAliasDeclarationY>(debugName) {

    override fun createPsi(stub: DLanguageAliasDeclarationYStub): DLanguageAliasDeclarationY {
        return DLanguageAliasDeclarationYImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode?): Boolean {
        return DUtil.definitionNode(node!!)
    }

    override fun createStub(psi: DLanguageAliasDeclarationY, parentStub: StubElement<*>): DLanguageAliasDeclarationYStub {
        return DLanguageAliasDeclarationYStub(parentStub, this, psi.name)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageAliasDeclarationYStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageAliasDeclarationYStub {
        return DLanguageAliasDeclarationYStub(parentStub, this, dataStream.readName()!!)
    }
}
