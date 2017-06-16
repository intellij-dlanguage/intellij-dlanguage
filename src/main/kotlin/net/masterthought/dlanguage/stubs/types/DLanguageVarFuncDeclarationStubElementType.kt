package net.masterthought.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import net.masterthought.dlanguage.psi.DLanguageVarFuncDeclaration
import net.masterthought.dlanguage.psi.impl.DLanguageVarFuncDeclarationImpl
import net.masterthought.dlanguage.stubs.DLanguageVarFuncDeclarationStub
import java.io.IOException

/**
 * Created by francis on 6/13/2017.
 */
class DLanguageVarFuncDeclarationStubElementType(debugName: String) : DNamedStubElementType<DLanguageVarFuncDeclarationStub, DLanguageVarFuncDeclaration>(debugName) {

    override fun createPsi(stub: DLanguageVarFuncDeclarationStub): DLanguageVarFuncDeclaration {
        return DLanguageVarFuncDeclarationImpl(stub, this)
    }

    override fun shouldCreateStub(node: ASTNode?): Boolean {
        return true
    }

    override fun createStub(psi: DLanguageVarFuncDeclaration, parentStub: StubElement<*>): DLanguageVarFuncDeclarationStub {
        return DLanguageVarFuncDeclarationStub(parentStub, this, psi.name)
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageVarFuncDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>): DLanguageVarFuncDeclarationStub {
        return DLanguageVarFuncDeclarationStub(parentStub, this, dataStream.readName()!!)
    }
}
