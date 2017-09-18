package net.masterthought.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import net.masterthought.dlanguage.psi.DLanguageEponymousTemplateDeclaration
import net.masterthought.dlanguage.psi.impl.named.DLanguageEponymousTemplateDeclarationImpl
import net.masterthought.dlanguage.stubs.DlangEponymousTemplateDeclarationStub
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DlangEponymousTemplateDeclarationStubElementType(debugName: String) : DNamedStubElementType<DlangEponymousTemplateDeclarationStub, DLanguageEponymousTemplateDeclaration>(debugName) {

    override fun createPsi(stub: DlangEponymousTemplateDeclarationStub): DLanguageEponymousTemplateDeclaration {
        return DLanguageEponymousTemplateDeclarationImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode?): Boolean {
        return true
    }

    override fun createStub(psi: DLanguageEponymousTemplateDeclaration, parentStub: StubElement<*>): DlangEponymousTemplateDeclarationStub {
        return DlangEponymousTemplateDeclarationStub(parentStub, this, psi.name)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangEponymousTemplateDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DlangEponymousTemplateDeclarationStub {
        return DlangEponymousTemplateDeclarationStub(parentStub, this, dataStream.readName()!!)
    }
}
