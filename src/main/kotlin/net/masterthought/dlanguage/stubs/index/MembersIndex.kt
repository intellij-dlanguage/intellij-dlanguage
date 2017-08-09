package net.masterthought.dlanguage.stubs.index

import com.intellij.psi.stubs.*
import net.masterthought.dlanguage.psi.interfaces.DNamedElement
import net.masterthought.dlanguage.psi.interfaces.HasMembers
import net.masterthought.dlanguage.stubs.*
import net.masterthought.dlanguage.stubs.interfaces.DLanguageUnittestStub
import java.util.*

/**
 * Created by francis on 8/8/2017.
 * index contains members of string key. Does not handle inheritance/mixins
 */
class DMembersIndex : StringStubIndexExtension<DNamedElement>() {

    override fun getVersion(): Int {
        return super.getVersion() + VERSION
    }

    override fun getKey(): StubIndexKey<String, DNamedElement> {
        return KEY
    }

    companion object {
        val KEY: StubIndexKey<String, DNamedElement> = StubIndexKey.createIndexKey<String, DNamedElement>("d.members")
        val VERSION = 1
        fun <S : NamedStubBase<*>> indexMembers(stub: S, sink: IndexSink) {
            for (hasMembers in getParentHasMembers(stub)) {
                sink.occurrence(DMembersIndex.KEY, hasMembers.name)
            }
        }

        private fun getParentHasMembers(stub: Stub): Set<HasMembers<*>> {
            val result = HashSet<HasMembers<*>>()
            getParentHasMembersImpl(stub, result)
            return result
        }

        private fun getParentHasMembersImpl(stub: Stub, result: MutableSet<HasMembers<*>>) {

            if (stub is HasMembers<*>) {
                result.add(stub as HasMembers<*>)
            }
            if (stub.parentStub == null) {
                return
            }
            getParentHasMembersImpl(stub.parentStub, result)
            if (stub is DLanguageUnittestStub || stub is DLanguageFunctionDeclarationStub || stub is DLanguageConstructorStub || stub is DLanguageSharedStaticConstructorStub || stub is DLanguageStaticConstructorStub || stub is DLanguageDestructorStub || stub is DLanguageSharedStaticDestructorStub || stub is DLanguageStaticDestructorStub) {
                result.clear()
                return
            }
        }

    }

}
