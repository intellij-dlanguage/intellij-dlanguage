package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.named.DlangFunctionDeclaration;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;

public class DlangFunctionDeclarationStub extends DNamedStubBase<DlangFunctionDeclaration> {

    public DlangFunctionDeclarationStub(final StubElement parent,
        final IStubElementType elementType, final StringRef name,
        DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }

    public DlangFunctionDeclarationStub(final StubElement parent,
        final IStubElementType elementType, final String name,
        DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }
}
