package io.github.intellij.dlanguage.stubs

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubElement
import com.intellij.util.io.StringRef
import io.github.intellij.dlanguage.psi.named.DLanguageUnionDeclaration
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes

class DlangUnionDeclarationStub : DNamedStubBase<DLanguageUnionDeclaration> {
    constructor(parent: StubElement<*>, elementType: IStubElementType<*, *>, name: StringRef?, attributes: DAttributes) : super(parent, elementType, name, attributes)

    constructor(parent: StubElement<*>, elementType: IStubElementType<*, *>, name: String?, attributes: DAttributes) : super(parent, elementType, name, attributes)
}
