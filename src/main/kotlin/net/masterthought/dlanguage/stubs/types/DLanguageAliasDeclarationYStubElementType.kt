package net.masterthought.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import net.masterthought.dlanguage.psi.DLanguageAliasDeclaration
import net.masterthought.dlanguage.stubs.DLanguageAliasInitializerStub
import net.masterthought.dlanguage.utils.DUtil
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */

class DLanguageAliasDeclarationYStubElementType(debugName: String) : DNamedStubElementType<DLanguageAliasInitializerStub, DLanguageAliasDeclaration>(debugName) {

    override fun createPsi(stub: DLanguageAliasInitializerStub): DLanguageAliasDeclaration {
        return DLanguageAliasDeclarationImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode?): Boolean {
        return DUtil.definitionNode(node!!)
    }

    override fun createStub(psi: DLanguageAliasDeclaration, parentStub: StubElement<*>): DLanguageAliasInitializerStub {
        return DLanguageAliasInitializerStub(parentStub, this, psi.name)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageAliasInitializerStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageAliasInitializerStub {
        return DLanguageAliasInitializerStub(parentStub, this, dataStream.readName()!!)
    }
}
