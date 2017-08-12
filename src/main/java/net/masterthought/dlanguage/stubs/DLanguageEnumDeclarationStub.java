package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import net.masterthought.dlanguage.psi.DLanguageEnumDeclaration;
import net.masterthought.dlanguage.stubs.interfaces.HasMembersStub;

/**
 * Created by francis on 3/9/2017.
 */
public class DLanguageEnumDeclarationStub extends NamedStubBase<DLanguageEnumDeclaration> implements HasMembersStub {
    public DLanguageEnumDeclarationStub(final StubElement parent, final IStubElementType elementType, final StringRef name) {
        super(parent, elementType, name);
    }

    public DLanguageEnumDeclarationStub(final StubElement parent, final IStubElementType elementType, final String name) {
        super(parent, elementType, name);
    }
}
