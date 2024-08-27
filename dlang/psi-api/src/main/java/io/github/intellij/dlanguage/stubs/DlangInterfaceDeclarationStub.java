package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.named.DlangInterfaceDeclaration;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.stubs.interfaces.HasMembersStub;

public class DlangInterfaceDeclarationStub extends DNamedStubBase<DlangInterfaceDeclaration> implements
    HasMembersStub {

    public DlangInterfaceDeclarationStub(final StubElement parent, final IStubElementType elementType,
                                         final StringRef name,
                                         DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }

    public DlangInterfaceDeclarationStub(final StubElement parent, final IStubElementType elementType,
                                         final String name,
                                         DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }
}
