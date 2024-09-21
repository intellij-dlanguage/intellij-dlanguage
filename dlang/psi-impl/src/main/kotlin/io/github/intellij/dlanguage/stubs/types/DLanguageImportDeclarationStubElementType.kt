package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import io.github.intellij.dlanguage.psi.DLanguageImportDeclaration
import io.github.intellij.dlanguage.psi.impl.DLanguageImportDeclarationImpl
import io.github.intellij.dlanguage.psi.interfaces.DVisibility
import io.github.intellij.dlanguage.psi.interfaces.DVisibilityStubKind
import io.github.intellij.dlanguage.psi.interfaces.stubKind
import io.github.intellij.dlanguage.stubs.DLanguageImportDeclarationStub

class DLanguageImportDeclarationStubElementType(debugName: String) : DStubElementType<DLanguageImportDeclarationStub, DLanguageImportDeclaration>(debugName) {
    override fun createPsi(stub: DLanguageImportDeclarationStub): DLanguageImportDeclaration {
        return DLanguageImportDeclarationImpl(stub, this)
    }

    override fun createStub(
        psi: DLanguageImportDeclaration,
        parentStub: StubElement<out PsiElement?>
    ): DLanguageImportDeclarationStub {
        return DLanguageImportDeclarationStub(
            parentStub,
            this,
            psi.visibility().stubKind,
            (psi.visibility() as? DVisibility.Package)?.name)
    }

    override fun serialize(stub: DLanguageImportDeclarationStub, dataStream: StubOutputStream) {
        dataStream.writeByte(stub.visibility.ordinal)
        if (stub.visibility == DVisibilityStubKind.PACKAGE_SPECIFIC)
            dataStream.writeUTF(stub.visibility.name)
    }

    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<out PsiElement?>): DLanguageImportDeclarationStub {
        val visibilityKind = enumValues<DVisibilityStubKind>()[dataStream.readUnsignedByte()]

        val packageNameRestriction = if (visibilityKind == DVisibilityStubKind.PACKAGE_SPECIFIC)
            dataStream.readUTF()
        else
            null
        return DLanguageImportDeclarationStub(
            parentStub,
            this,
            visibilityKind,
            packageNameRestriction
        )
    }
}
