package io.github.intellij.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.DLanguageTemplateTypeParameter
import io.github.intellij.dlanguage.psi.impl.named.DLanguageTemplateTypeParameterImpl
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.stubs.DLanguageTemplateTypeParameterStub
import io.github.intellij.dlanguage.utils.DUtil
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DlangTemplateTypeParameterStubElementType(debugName: String) : DNamedStubElementType<DLanguageTemplateTypeParameterStub, DLanguageTemplateTypeParameter>(debugName) {

    override fun createPsi(stub: DLanguageTemplateTypeParameterStub): DLanguageTemplateTypeParameter {
        return DLanguageTemplateTypeParameterImpl(
            stub,
            this
        )
    }

    override fun shouldCreateStub(node: ASTNode): Boolean {
        return DUtil.definitionNode(node) && super.shouldCreateStub(node)
    }

    override fun createStub(psi: DLanguageTemplateTypeParameter, parentStub: StubElement<*>): DLanguageTemplateTypeParameterStub {
        return DLanguageTemplateTypeParameterStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageTemplateTypeParameterStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageTemplateTypeParameterStub {
        return DLanguageTemplateTypeParameterStub(parentStub, this, dataStream.readName(),
            DAttributes.read(dataStream))
    }
}
