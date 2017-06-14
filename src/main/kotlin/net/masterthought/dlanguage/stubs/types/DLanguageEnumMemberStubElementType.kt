package net.masterthought.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import net.masterthought.dlanguage.psi.DLanguageEnumMember
import net.masterthought.dlanguage.psi.impl.DLanguageEnumMemberImpl
import net.masterthought.dlanguage.stubs.DLanguageEnumMemberStub
import net.masterthought.dlanguage.utils.DUtil
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DLanguageEnumMemberStubElementType(debugName: String) : DNamedStubElementType<DLanguageEnumMemberStub, DLanguageEnumMember>(debugName) {

    override fun createPsi(stub: DLanguageEnumMemberStub): DLanguageEnumMember {
        return DLanguageEnumMemberImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode?): Boolean {
        return DUtil.definitionNode(node!!)
    }

    override fun createStub(psi: DLanguageEnumMember, parentStub: StubElement<*>): DLanguageEnumMemberStub {
        return DLanguageEnumMemberStub(parentStub, this, psi.name)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageEnumMemberStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageEnumMemberStub {
        return DLanguageEnumMemberStub(parentStub, this, dataStream.readName()!!)
    }
}
