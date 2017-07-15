package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import net.masterthought.dlanguage.psi.DLanguageClassDeclaration;
import net.masterthought.dlanguage.psi.DLanguageInterfaceOrClass;

public class DLanguageInterfaceOrClassDeclarationStub extends NamedStubBase<DLanguageInterfaceOrClass> {
    public DLanguageInterfaceOrClassDeclarationStub(StubElement parent, IStubElementType elementType, StringRef name) {
        super(parent, elementType, name);
    }

    public DLanguageInterfaceOrClassDeclarationStub(StubElement parent, IStubElementType elementType, String name) {
        super(parent, elementType, name);
    }
}
