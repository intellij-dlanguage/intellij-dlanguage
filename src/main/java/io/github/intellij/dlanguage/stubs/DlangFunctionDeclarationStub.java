package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.DLanguageFunctionDeclaration;
import io.github.intellij.dlanguage.psi.DLanguageFunctionDeclaration;

public class DlangFunctionDeclarationStub extends NamedStubBase<DLanguageFunctionDeclaration> {
    public DlangFunctionDeclarationStub(final StubElement parent, final IStubElementType elementType, final StringRef name) {
        super(parent, elementType, name);
    }

    public DlangFunctionDeclarationStub(final StubElement parent, final IStubElementType elementType, final String name) {
        super(parent, elementType, name);
    }
}
