package io.github.intellij.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.DLanguageTemplateValueParameter
import io.github.intellij.dlanguage.psi.impl.named.DLanguageTemplateValueParameterImpl
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.stubs.DLanguageTemplateValueParameterStub
import io.github.intellij.dlanguage.utils.DUtil
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DlangTemplateValueParameterStubElementType(debugName: String) : DNamedStubElementType<DLanguageTemplateValueParameterStub, DLanguageTemplateValueParameter>(debugName) {

    override fun createPsi(stub: DLanguageTemplateValueParameterStub): DLanguageTemplateValueParameter {
        return DLanguageTemplateValueParameterImpl(
            stub,
            this
        )
    }

    override fun shouldCreateStub(node: ASTNode): Boolean {
        return DUtil.definitionNode(node) && super.shouldCreateStub(node)
    }

    override fun createStub(psi: DLanguageTemplateValueParameter, parentStub: StubElement<*>): DLanguageTemplateValueParameterStub {
        return DLanguageTemplateValueParameterStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageTemplateValueParameterStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageTemplateValueParameterStub {
        return DLanguageTemplateValueParameterStub(parentStub, this, dataStream.readName(),
            DAttributes.read(dataStream))
    }
}
