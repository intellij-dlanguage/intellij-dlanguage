package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import net.masterthought.dlanguage.psi.DLanguageInterfaceDeclaration;

public class DLanguageInterfaceDeclarationStub extends NamedStubBase<DLanguageInterfaceDeclaration> {
    public DLanguageInterfaceDeclarationStub(StubElement parent, IStubElementType elementType, StringRef name) {
        super(parent, elementType, name);
    }

    public DLanguageInterfaceDeclarationStub(StubElement parent, IStubElementType elementType, String name) {
        super(parent, elementType, name);
    }
}
