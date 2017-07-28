package net.masterthought.dlanguage.stubs

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.NamedStubBase
import com.intellij.psi.stubs.StubElement
import com.intellij.util.io.StringRef
import net.masterthought.dlanguage.psi.DLanguageAliasDeclaration
import net.masterthought.dlanguage.psi.DLanguageAliasInitializer

/**
 * Created by francis on 6/13/2017.
 */
class DLanguageAliasInitializerStub : NamedStubBase<DLanguageAliasInitializer> {
    constructor(parent: StubElement<*>, elementType: IStubElementType<*, *>, name: StringRef?) : super(parent, elementType, name)

    constructor(parent: StubElement<*>, elementType: IStubElementType<*, *>, name: String?) : super(parent, elementType, name)

}
