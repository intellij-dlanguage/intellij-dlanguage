package io.github.intellij.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.DLanguageTemplateParameter
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.stubs.DlangTemplateParameterStub
import io.github.intellij.dlanguage.stubs.types.DNamedStubElementType
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DlangTemplateParameterStubElementType(debugName: String) : DNamedStubElementType<DlangTemplateParameterStub, DLanguageTemplateParameter>(debugName) {

    override fun createPsi(stub: DlangTemplateParameterStub): io.github.intellij.dlanguage.psi.DLanguageTemplateParameter {
        return io.github.intellij.dlanguage.psi.impl.named.DLanguageTemplateParameterImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode?): Boolean {
        return io.github.intellij.dlanguage.utils.DUtil.definitionNode(node!!)
    }

    override fun createStub(psi: io.github.intellij.dlanguage.psi.DLanguageTemplateParameter, parentStub: StubElement<*>): DlangTemplateParameterStub {
        return DlangTemplateParameterStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangTemplateParameterStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DlangTemplateParameterStub {
        return DlangTemplateParameterStub(parentStub, this, dataStream.readName()!!,
            DAttributes.read(dataStream))
    }
}
