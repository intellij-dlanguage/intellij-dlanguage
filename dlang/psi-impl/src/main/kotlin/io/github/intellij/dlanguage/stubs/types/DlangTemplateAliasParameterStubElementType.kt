package io.github.intellij.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.DLanguageTemplateAliasParameter
import io.github.intellij.dlanguage.psi.impl.named.DLanguageTemplateAliasParameterImpl
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.stubs.DLanguageTemplateAliasParameterStub
import io.github.intellij.dlanguage.utils.DUtil
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DlangTemplateAliasParameterStubElementType(debugName: String) : DNamedStubElementType<DLanguageTemplateAliasParameterStub, DLanguageTemplateAliasParameter>(debugName) {

    override fun createPsi(stub: DLanguageTemplateAliasParameterStub): DLanguageTemplateAliasParameter {
        return DLanguageTemplateAliasParameterImpl(
            stub,
            this
        )
    }

    override fun shouldCreateStub(node: ASTNode): Boolean {
        return DUtil.definitionNode(node) && super.shouldCreateStub(node)
    }

    override fun createStub(psi: DLanguageTemplateAliasParameter, parentStub: StubElement<*>): DLanguageTemplateAliasParameterStub {
        return DLanguageTemplateAliasParameterStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageTemplateAliasParameterStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageTemplateAliasParameterStub {
        return DLanguageTemplateAliasParameterStub(parentStub, this, dataStream.readName(),
            DAttributes.read(dataStream))
    }
}
