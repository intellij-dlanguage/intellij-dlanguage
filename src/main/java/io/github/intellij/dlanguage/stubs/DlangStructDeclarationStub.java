package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.DlangStructDeclaration;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.stubs.interfaces.HasMembersStub;

public class DlangStructDeclarationStub extends DNamedStubBase<DlangStructDeclaration> implements
    HasMembersStub {

    public DlangStructDeclarationStub(final StubElement parent, final IStubElementType elementType,
        final StringRef name,
        DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }

    public DlangStructDeclarationStub(final StubElement parent, final IStubElementType elementType,
        final String name,
        DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }
}
