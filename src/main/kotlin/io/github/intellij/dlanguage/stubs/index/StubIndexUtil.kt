package io.github.intellij.dlanguage.stubs.index

import com.intellij.psi.stubs.NamedStubBase
import com.intellij.psi.stubs.StubElement
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.stubs.*
import io.github.intellij.dlanguage.stubs.interfaces.DlangUnittestStub

/**
 * Created by francis on 8/8/2017.
 */
fun <S : NamedStubBase<T>, T : io.github.intellij.dlanguage.psi.interfaces.DNamedElement> topLevelDeclaration(stub: S): Boolean {
    //stuff within unittests does not count as top level
    //stuff within func declarations does not count as top level b/c not globally accessible todo check if this is true for all declaration types
    //switch the topLevel declaration to a file gist maybe

    if (stub is DlangParameterStub || stub is DlangForeachTypeStub || stub is DlangTemplateParameterStub) {
        return false
    }


    var stubParent: StubElement<*>? = stub
    while (true) {
        stubParent = stubParent!!.parentStub
        if (stubParent == null) {
            return true
        }
        if (stubParent is io.github.intellij.dlanguage.stubs.DlangFunctionDeclarationStub) {
            return false
        }
        if (stubParent is io.github.intellij.dlanguage.stubs.DlangConstructorStub || stubParent is io.github.intellij.dlanguage.stubs.DlangSharedStaticConstructorStub || stubParent is io.github.intellij.dlanguage.stubs.DlangStaticConstructorStub || stubParent is io.github.intellij.dlanguage.stubs.DlangDestructorStub || stubParent is io.github.intellij.dlanguage.stubs.DlangSharedStaticDestructorStub || stubParent is io.github.intellij.dlanguage.stubs.DlangStaticDestructorStub) {
            return false
        }
        if (stubParent is io.github.intellij.dlanguage.stubs.interfaces.DlangUnittestStub) {
            return false
        }
    }
}
