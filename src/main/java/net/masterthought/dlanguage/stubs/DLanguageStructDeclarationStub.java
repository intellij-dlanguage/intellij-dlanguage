package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import net.masterthought.dlanguage.psi.DLanguageStructDeclaration;
import net.masterthought.dlanguage.stubs.interfaces.HasMembersStub;

public class DLanguageStructDeclarationStub extends NamedStubBase<DLanguageStructDeclaration> implements HasMembersStub {
    public DLanguageStructDeclarationStub(final StubElement parent, final IStubElementType elementType, final StringRef name) {
        super(parent, elementType, name);
    }

    public DLanguageStructDeclarationStub(final StubElement parent, final IStubElementType elementType, final String name) {
        super(parent, elementType, name);
    }
}
