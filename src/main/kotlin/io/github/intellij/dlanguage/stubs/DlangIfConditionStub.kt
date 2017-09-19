package io.github.intellij.dlanguage.stubs

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.NamedStubBase
import com.intellij.psi.stubs.StubElement
import com.intellij.util.io.StringRef
import io.github.intellij.dlanguage.psi.DLanguageIfCondition

/**
 * Created by francis on 6/13/2017.
 */

class DlangIfConditionStub : NamedStubBase<io.github.intellij.dlanguage.psi.DLanguageIfCondition> {
    constructor(parent: StubElement<*>, elementType: IStubElementType<*, *>, name: StringRef?) : super(parent, elementType, name)

    constructor(parent: StubElement<*>, elementType: IStubElementType<*, *>, name: String?) : super(parent, elementType, name)
}
