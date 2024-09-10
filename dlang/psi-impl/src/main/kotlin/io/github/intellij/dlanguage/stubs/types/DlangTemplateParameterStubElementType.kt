package io.github.intellij.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.DLanguageTemplateParameter
import io.github.intellij.dlanguage.psi.impl.named.DLanguageTemplateParameterImpl
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.stubs.DLanguageTemplateParameterStub
import io.github.intellij.dlanguage.utils.DUtil
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DlangTemplateParameterStubElementType(debugName: String) : DNamedStubElementType<DLanguageTemplateParameterStub, DLanguageTemplateParameter>(debugName) {

    override fun createPsi(stub: DLanguageTemplateParameterStub): DLanguageTemplateParameter {
        return DLanguageTemplateParameterImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode): Boolean {
        return DUtil.definitionNode(node) && super.shouldCreateStub(node)
    }

    override fun createStub(psi: DLanguageTemplateParameter, parentStub: StubElement<*>): DLanguageTemplateParameterStub {
        return DLanguageTemplateParameterStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageTemplateParameterStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageTemplateParameterStub {
        return DLanguageTemplateParameterStub(parentStub, this, dataStream.readName(),
            DAttributes.read(dataStream))
    }
}
