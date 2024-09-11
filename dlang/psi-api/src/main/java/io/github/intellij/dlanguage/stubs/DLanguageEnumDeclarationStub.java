package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.named.DLanguageEnumDeclaration;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.stubs.interfaces.HasMembersStub;

/**
 * Created by francis on 3/9/2017.
 */
public class DLanguageEnumDeclarationStub extends DNamedStubBase<DLanguageEnumDeclaration> implements
    HasMembersStub {

    public DLanguageEnumDeclarationStub(final StubElement parent, final IStubElementType elementType,
                                        final StringRef name,
                                        final DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }

    public DLanguageEnumDeclarationStub(final StubElement parent, final IStubElementType elementType,
                                        final String name,
                                        final DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }
}
