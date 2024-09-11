package io.github.intellij.dlanguage.stubs.index

import com.intellij.psi.stubs.NamedStubBase
import com.intellij.psi.stubs.StubElement
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.stubs.*
import io.github.intellij.dlanguage.stubs.DLanguageUnittestStub

/**
 * Created by francis on 8/8/2017.
 */
fun <S : NamedStubBase<T>, T : DNamedElement> topLevelDeclaration(stub: S): Boolean {
    //stuff within unittests does not count as top level
    //stuff within func declarations does not count as top level b/c not globally accessible todo check if this is true for all declaration types
    //switch the topLevel declaration to a file gist maybe

    if (stub is DLanguageParameterStub || stub is DLanguageForeachTypeStub || stub is DLanguageTemplateAliasParameterStub) {
        return false
    }

    if (stub is DLanguageEnumMemberStub) {
        return stub.parentStub !is DLanguageEnumDeclarationStub
    }


    var stubParent: StubElement<*>? = stub
    while (true) {
        stubParent = stubParent!!.parentStub
        if (stubParent == null) {
            return true
        }
        if (stubParent is DLanguageFunctionDeclarationStub) {
            return false
        }
        if (stubParent is DLanguageConstructorStub || stubParent is DLanguageSharedStaticConstructorStub || stubParent is DLanguageStaticConstructorStub || stubParent is DLanguageDestructorStub || stubParent is DLanguageSharedStaticDestructorStub || stubParent is DLanguageStaticDestructorStub) {
            return false
        }
        if (stubParent is DLanguageStructDeclarationStub || stubParent is DLanguageInterfaceDeclarationStub || stubParent is DLanguageClassDeclarationStub) {
            return false
        }
        if (stubParent is DLanguageUnittestStub) {
            return false
        }
    }
}
