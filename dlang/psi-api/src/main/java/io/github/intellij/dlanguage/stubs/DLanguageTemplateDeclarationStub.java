package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.named.DLanguageTemplateDeclaration;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis nixon on 1/11/2017.
 */
public class DLanguageTemplateDeclarationStub extends DNamedStubBase<DLanguageTemplateDeclaration> {

    public DLanguageTemplateDeclarationStub(final StubElement parent,
                                            @NotNull final IStubElementType elementType, final StringRef name,
                                            final DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }

    public DLanguageTemplateDeclarationStub(final StubElement parent,
                                            final IStubElementType elementType, final String name,
                                            final DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }
}
