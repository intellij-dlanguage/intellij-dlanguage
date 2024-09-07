package io.github.intellij.dlanguage.stubs.types

import com.intellij.psi.PsiFile
import com.intellij.psi.StubBuilder
import com.intellij.psi.stubs.DefaultStubBuilder
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import com.intellij.psi.tree.IStubFileElementType
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.psi.DlangPsiFileImpl
import io.github.intellij.dlanguage.stubs.DlangFileStub
import java.io.IOException

class DFileStubElementType : IStubFileElementType<DlangFileStub>("FILE", DLanguage) {
    override fun getBuilder(): StubBuilder {
        return object : DefaultStubBuilder() {
            override fun createStubForFile(file: PsiFile): StubElement<*> {
                if (file is DlangPsiFileImpl) {
                    return DlangFileStub(file)
                }
                return super.createStubForFile(file)
            }
        }
    }

    override fun getStubVersion(): Int {
        return VERSION
    }

    @Throws(IOException::class)
    override fun serialize(stub: DlangFileStub, dataStream: StubOutputStream) {
        // todo make files named?
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DlangFileStub {
        return DlangFileStub(null)
    }

    override fun getExternalId(): String {
        return "d.FILE"
    }

    companion object {
        const val VERSION: Int = 1
        val INSTANCE: DFileStubElementType = DFileStubElementType()
    }
}

