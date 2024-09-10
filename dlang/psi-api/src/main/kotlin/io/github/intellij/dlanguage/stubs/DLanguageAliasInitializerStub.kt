package io.github.intellij.dlanguage.stubs

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubElement
import com.intellij.util.io.StringRef
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes
import io.github.intellij.dlanguage.utils.AliasInitializer

/**
 * Created by francis on 6/13/2017.
 */
class DLanguageAliasInitializerStub : DNamedStubBase<AliasInitializer> {
    constructor(parent: StubElement<*>, elementType: IStubElementType<*, *>, name: StringRef?, attributes: DAttributes) : super(parent, elementType, name, attributes)

    constructor(parent: StubElement<*>, elementType: IStubElementType<*, *>, name: String?, attributes: DAttributes) : super(parent, elementType, name, attributes)

}
