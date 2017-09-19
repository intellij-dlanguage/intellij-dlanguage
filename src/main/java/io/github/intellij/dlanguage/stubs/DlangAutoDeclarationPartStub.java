package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.DLanguageAutoDeclarationPart;
import io.github.intellij.dlanguage.psi.DLanguageAutoDeclarationPart;

public class DlangAutoDeclarationPartStub extends NamedStubBase<DLanguageAutoDeclarationPart> {
    public DlangAutoDeclarationPartStub(final StubElement parent, final IStubElementType elementType, final StringRef name) {
        super(parent, elementType, name);
    }

    public DlangAutoDeclarationPartStub(final StubElement parent, final IStubElementType elementType, final String name) {
        super(parent, elementType, name);
    }
}
