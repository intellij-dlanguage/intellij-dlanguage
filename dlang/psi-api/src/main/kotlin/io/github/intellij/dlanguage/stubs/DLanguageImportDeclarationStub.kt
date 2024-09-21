package io.github.intellij.dlanguage.stubs

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubBase
import com.intellij.psi.stubs.StubElement
import io.github.intellij.dlanguage.psi.DLanguageImportDeclaration
import io.github.intellij.dlanguage.psi.interfaces.DVisibilityStubKind

class DLanguageImportDeclarationStub(
    val parent: StubElement<*>,
    val elementType: IStubElementType<*, *>,
    val visibility: DVisibilityStubKind,
    val packageVisibilityName: String?) : StubBase<DLanguageImportDeclaration>(parent, elementType) {
}
