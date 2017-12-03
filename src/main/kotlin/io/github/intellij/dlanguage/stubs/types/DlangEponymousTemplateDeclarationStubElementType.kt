package io.github.intellij.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.stubs.DlangEponymousTemplateDeclarationStub
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DlangEponymousTemplateDeclarationStubElementType(debugName: String) : io.github.intellij.dlanguage.stubs.types.DNamedStubElementType<DlangEponymousTemplateDeclarationStub, io.github.intellij.dlanguage.psi.DLanguageEponymousTemplateDeclaration>(debugName) {

    override fun createPsi(stub: DlangEponymousTemplateDeclarationStub): io.github.intellij.dlanguage.psi.DLanguageEponymousTemplateDeclaration {
        return io.github.intellij.dlanguage.psi.impl.named.DLanguageEponymousTemplateDeclarationImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode?): Boolean {
        return true
    }

    override fun createStub(psi: io.github.intellij.dlanguage.psi.DLanguageEponymousTemplateDeclaration, parentStub: StubElement<*>): DlangEponymousTemplateDeclarationStub {
        return DlangEponymousTemplateDeclarationStub(parentStub, this, psi.name, psi.attributes)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangEponymousTemplateDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DlangEponymousTemplateDeclarationStub {
        return DlangEponymousTemplateDeclarationStub(parentStub, this, dataStream.readName()!!,
            DAttributes.read(dataStream))
    }
}
