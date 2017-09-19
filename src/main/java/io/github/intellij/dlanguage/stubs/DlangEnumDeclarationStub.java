package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.DlangEnumDeclaration;
import io.github.intellij.dlanguage.stubs.interfaces.HasMembersStub;

/**
 * Created by francis on 3/9/2017.
 */
public class DlangEnumDeclarationStub extends NamedStubBase<DlangEnumDeclaration> implements HasMembersStub {
    public DlangEnumDeclarationStub(final StubElement parent, final IStubElementType elementType, final StringRef name) {
        super(parent, elementType, name);
    }

    public DlangEnumDeclarationStub(final StubElement parent, final IStubElementType elementType, final String name) {
        super(parent, elementType, name);
    }
}
