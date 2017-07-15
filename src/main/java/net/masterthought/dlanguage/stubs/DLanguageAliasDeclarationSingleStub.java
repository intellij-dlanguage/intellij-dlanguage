package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;

public class DLanguageAliasDeclarationSingleStub extends NamedStubBase<DLanguageAliasDeclarationSingle> {
    public DLanguageAliasDeclarationSingleStub(StubElement parent, IStubElementType elementType, StringRef name) {
        super(parent, elementType, name);
    }

    public DLanguageAliasDeclarationSingleStub(StubElement parent, IStubElementType elementType, String name) {
        super(parent, elementType, name);
    }
}
