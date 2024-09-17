package io.github.intellij.dlanguage.stubs.index

import com.intellij.psi.stubs.NamedStubBase
import com.intellij.psi.stubs.StubElement
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.psi.interfaces.DVisibilityStubKind
import io.github.intellij.dlanguage.stubs.*
import io.github.intellij.dlanguage.stubs.DLanguageUnittestStub

/**
 * Created by francis on 8/8/2017.
 */
fun <S : NamedStubBase<T>, T : DNamedElement> topLevelDeclaration(stub: S): Boolean {
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
        //stuff within func declarations does not count as top level b/c not globally accessible
        if (stubParent is DLanguageFunctionDeclarationStub) {
            return false
        }
        if (stubParent is DLanguageConstructorStub || stubParent is DLanguageSharedStaticConstructorStub || stubParent is DLanguageStaticConstructorStub || stubParent is DLanguageDestructorStub || stubParent is DLanguageSharedStaticDestructorStub || stubParent is DLanguageStaticDestructorStub) {
            return false
        }
        if (stubParent is DLanguageStructDeclarationStub || stubParent is DLanguageInterfaceDeclarationStub || stubParent is DLanguageClassDeclarationStub) {
            return false
        }
        //stuff within unittests does not count as top level
        if (stubParent is DLanguageUnittestStub) {
            return false
        }
        if (stubParent is DLanguageImportDeclarationStub && stubParent.visibility != DVisibilityStubKind.PUBLIC) {
            return false
        }
    }
}
