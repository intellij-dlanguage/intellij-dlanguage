package io.github.intellij.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.DLanguageTemplateTupleParameter
import io.github.intellij.dlanguage.psi.impl.named.DLanguageTemplateTupleParameterImpl
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.stubs.DLanguageTemplateTupleParameterStub
import io.github.intellij.dlanguage.utils.DUtil
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DlangTemplateTupleParameterStubElementType(debugName: String) : DNamedStubElementType<DLanguageTemplateTupleParameterStub, DLanguageTemplateTupleParameter>(debugName) {

    override fun createPsi(stub: DLanguageTemplateTupleParameterStub): DLanguageTemplateTupleParameter {
        return DLanguageTemplateTupleParameterImpl(
            stub,
            this
        )
    }

    override fun shouldCreateStub(node: ASTNode): Boolean {
        return DUtil.definitionNode(node) && super.shouldCreateStub(node)
    }

    override fun createStub(psi: DLanguageTemplateTupleParameter, parentStub: StubElement<*>): DLanguageTemplateTupleParameterStub {
        return DLanguageTemplateTupleParameterStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageTemplateTupleParameterStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageTemplateTupleParameterStub {
        return DLanguageTemplateTupleParameterStub(parentStub, this, dataStream.readName(),
            DAttributes.read(dataStream))
    }
}
