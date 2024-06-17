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
class DMembersIndex : StringStubIndexExtension<DNamedElement>() {

    override fun getVersion(): Int {
        return super.getVersion() + VERSION
    }

    //use with caution, prefer static methods
    override fun getKey(): StubIndexKey<String, DNamedElement> {
        return KEY
    }

    companion object {
        private val KEY: StubIndexKey<String, DNamedElement> = StubIndexKey.createIndexKey<String, DNamedElement>("d.globally.members")
        val VERSION = 3
        fun <S : NamedStubBase<*>> indexMembers(stub: S, sink: IndexSink) {
            if (getParentHasMembers(stub).size > 1)
                return
            for (hasMembers in getParentHasMembers(stub)) {
                sink.occurrence(DMembersIndex.KEY, hasMembers.name)
            }
        }

        fun getMemberSymbols(name: String, module: String, project: Project): Set<DNamedElement> {
            val elements = mutableSetOf<DNamedElement>()
            for (file in DModuleIndex.getFilesByModuleName(project, module, GlobalSearchScope.allScope(project))) {
                elements.addAll(StubIndex.getElements(KEY, name, project, GlobalSearchScope.fileScope(file), DNamedElement::class.java))//todp assert that this should only be called once
            }
            return elements
        }

        private fun getParentHasMembers(stub: Stub): Set<HasMembersStub> {
            val result = HashSet<HasMembersStub>()
            getParentHasMembersImpl(stub, result)
            return result
        }

        private fun getParentHasMembersImpl(stub: Stub, result: MutableSet<HasMembersStub>) {
            if (stub is HasMembersStub) {
                result.add(stub as HasMembersStub)
            }
            if (stub.parentStub == null) {
                return
            }
            getParentHasMembersImpl(stub.parentStub, result)
            if (stub.parentStub is DlangUnittestStub || stub.parentStub is DlangFunctionDeclarationStub || stub.parentStub is DlangConstructorStub || stub.parentStub is DlangSharedStaticConstructorStub || stub.parentStub is DlangStaticConstructorStub || stub.parentStub is DlangDestructorStub || stub.parentStub is DlangSharedStaticDestructorStub || stub.parentStub is DlangStaticDestructorStub) {
                result.clear()
            }
        }
    }
}
