package io.github.intellij.dlanguage.stubs.index

import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.*
import io.github.intellij.dlanguage.index.DModuleIndex
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.stubs.*
import io.github.intellij.dlanguage.stubs.interfaces.DlangUnittestStub
import io.github.intellij.dlanguage.stubs.interfaces.HasMembersStub
import java.util.*

/**
 * Created by francis on 8/8/2017.
 * index contains members of string key. Does not handle inheritance/mixins
 */
class DMembersIndex : StringStubIndexExtension<io.github.intellij.dlanguage.psi.interfaces.DNamedElement>() {

    override fun getVersion(): Int {
        return super.getVersion() + VERSION
    }

    //use with caution, prefer static methods
    override fun getKey(): StubIndexKey<String, io.github.intellij.dlanguage.psi.interfaces.DNamedElement> {
        return KEY
    }

    companion object {
        private val KEY: StubIndexKey<String, io.github.intellij.dlanguage.psi.interfaces.DNamedElement> = StubIndexKey.createIndexKey<String, io.github.intellij.dlanguage.psi.interfaces.DNamedElement>("d.globally.members")
        val VERSION = 1
        fun <S : NamedStubBase<*>> indexMembers(stub: S, sink: IndexSink) {
            if (getParentHasMembers(stub).size > 1)
                return
            for (hasMembers in getParentHasMembers(stub)) {
                if (hasMembers.name == null) {
                    throw IllegalStateException()
                }
                sink.occurrence(DMembersIndex.KEY, hasMembers.name)
            }
        }

        fun getMemberSymbols(name: String, module: String, project: Project): Set<io.github.intellij.dlanguage.psi.interfaces.DNamedElement> {
            val elements = mutableSetOf<io.github.intellij.dlanguage.psi.interfaces.DNamedElement>()
            for (file in io.github.intellij.dlanguage.index.DModuleIndex.getFilesByModuleName(project, module, GlobalSearchScope.everythingScope(project))) {
                elements.addAll(StubIndex.getElements(KEY, name, project, GlobalSearchScope.fileScope(file), io.github.intellij.dlanguage.psi.interfaces.DNamedElement::class.java))//todp assert that this should only be called once
            }
            return elements
        }

        private fun getParentHasMembers(stub: Stub): Set<io.github.intellij.dlanguage.stubs.interfaces.HasMembersStub> {
            val result = HashSet<io.github.intellij.dlanguage.stubs.interfaces.HasMembersStub>()
            getParentHasMembersImpl(stub, result)
            return result
        }

        private fun getParentHasMembersImpl(stub: Stub, result: MutableSet<io.github.intellij.dlanguage.stubs.interfaces.HasMembersStub>) {
            if (stub is io.github.intellij.dlanguage.stubs.interfaces.HasMembersStub) {
                result.add(stub as io.github.intellij.dlanguage.stubs.interfaces.HasMembersStub)
            }
            if (stub.parentStub == null) {
                return
            }
            getParentHasMembersImpl(stub.parentStub, result)
            if (stub.parentStub is io.github.intellij.dlanguage.stubs.interfaces.DlangUnittestStub || stub.parentStub is io.github.intellij.dlanguage.stubs.DlangFunctionDeclarationStub || stub.parentStub is io.github.intellij.dlanguage.stubs.DlangConstructorStub || stub.parentStub is io.github.intellij.dlanguage.stubs.DlangSharedStaticConstructorStub || stub.parentStub is io.github.intellij.dlanguage.stubs.DlangStaticConstructorStub || stub.parentStub is io.github.intellij.dlanguage.stubs.DlangDestructorStub || stub.parentStub is io.github.intellij.dlanguage.stubs.DlangSharedStaticDestructorStub || stub.parentStub is io.github.intellij.dlanguage.stubs.DlangStaticDestructorStub) {
                result.clear()
            }
        }
    }
}
