package io.github.intellij.dlanguage.stubs.types

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import com.intellij.util.io.StringRef
import io.github.intellij.dlanguage.psi.impl.named.DlangSingleImportImpl
import io.github.intellij.dlanguage.psi.named.DLanguageSingleImport
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes.Companion.read
import io.github.intellij.dlanguage.stubs.DLanguageSingleImportStub
import java.io.IOException
import java.util.HashMap
import java.util.HashSet

/**
 * Created by francis on 3/15/2017.
 */
class SingleImportStubElementType(debugName: String) :
    DNamedStubElementType<DLanguageSingleImportStub, DLanguageSingleImport>(debugName) {
    override fun createPsi(stub: DLanguageSingleImportStub): DLanguageSingleImport {
        return DlangSingleImportImpl(stub, this)
    }

    override fun createStub(psi: DLanguageSingleImport, parentStub: StubElement<*>?): DLanguageSingleImportStub {
        val binds: MutableSet<StringRef> = HashSet<StringRef>()
        for (bind in psi.getApplicableImportBinds()) {
            binds.add(StringRef.fromString(bind))
        }
        val namedBinds: MutableMap<StringRef, StringRef> = HashMap<StringRef, StringRef>()
        psi.getApplicableNamedImportBinds().forEach { (bindName: String?, bind: String?) ->
            namedBinds.put(
                StringRef.fromString(bindName),
                StringRef.fromString(bind)
            )
        }
        return DLanguageSingleImportStub(
            parentStub, this, psi.getName(),
            psi.getApplicableImportBinds().size, binds,
            psi.getApplicableNamedImportBinds().size, namedBinds,
            psi.getImportedModuleName(),
            psi.getIdentifier() != null,
            psi.getIdentifier()?.text,
            psi.getAttributes()
        )
    }

    @Throws(IOException::class)
    override fun serialize(stub: DLanguageSingleImportStub, dataStream: StubOutputStream) {
        dataStream.writeName(stub.name)
        dataStream.writeInt(stub.numBinds())
        for (s in stub.binds) {
            dataStream.writeName(s)
        }
        dataStream.writeInt(stub.numNamedBinds())
        for (entry in stub.namedBinds.entries) {
            dataStream.writeName(entry.key)
            dataStream.writeName(entry.value)
        }
        dataStream.writeName(stub.name)
        dataStream.writeBoolean(stub.hasName())
        check(stub.importedModule != "")
        dataStream.writeName(stub.importedModule)
        stub.attributes.write(dataStream)
    }

    @Throws(IOException::class)
    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): DLanguageSingleImportStub {
        val name = dataStream.readName()
        val numBinds = dataStream.readInt()
        val binds: MutableSet<StringRef?> = HashSet<StringRef?>()
        for (i in 0 until numBinds) {
            binds.add(dataStream.readName())
        }
        val numNamedBinds = dataStream.readInt()
        val namedBinds: MutableMap<StringRef?, StringRef?> = HashMap<StringRef?, StringRef?>()
        for (i in 0 until numNamedBinds) {
            namedBinds.put(dataStream.readName(), dataStream.readName())
        }
        val importName = dataStream.readName()
        val hasName = dataStream.readBoolean()
        val importedModule = dataStream.readName()
        val attributes = read(dataStream)
        return DLanguageSingleImportStub(
            parentStub, this, name, numBinds, binds, numNamedBinds, namedBinds,
            importName, hasName, importedModule, attributes
        )
    }

    override fun shouldCreateStub(node: ASTNode): Boolean {
        return !(node.psi as DLanguageSingleImport).getImportedModuleName().isBlank()
    }
}
