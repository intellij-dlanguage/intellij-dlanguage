package net.masterthought.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import net.masterthought.dlanguage.psi.DLanguageTemplateParameter
import net.masterthought.dlanguage.psi.impl.named.DLanguageTemplateParameterImpl
import net.masterthought.dlanguage.stubs.DlangTemplateParameterStub
import net.masterthought.dlanguage.utils.DUtil
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DlangTemplateParameterStubElementType(debugName: String) : DNamedStubElementType<DlangTemplateParameterStub, DLanguageTemplateParameter>(debugName) {

    override fun createPsi(stub: DlangTemplateParameterStub): DLanguageTemplateParameter {
        return DLanguageTemplateParameterImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode?): Boolean {
        return DUtil.definitionNode(node!!)
    }

    override fun createStub(psi: DLanguageTemplateParameter, parentStub: StubElement<*>): DlangTemplateParameterStub {
        return DlangTemplateParameterStub(parentStub, this, psi.name)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangTemplateParameterStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DlangTemplateParameterStub {
        return DlangTemplateParameterStub(parentStub, this, dataStream.readName()!!)
    }
}
