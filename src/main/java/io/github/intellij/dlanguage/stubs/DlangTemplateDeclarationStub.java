package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.DlangTemplateDeclaration;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis nixon on 1/11/2017.
 */
public class DlangTemplateDeclarationStub extends DNamedStubBase<DlangTemplateDeclaration> {

    public DlangTemplateDeclarationStub(final StubElement parent,
        @NotNull final IStubElementType elementType, final StringRef name,
        DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }

    public DlangTemplateDeclarationStub(final StubElement parent,
        final IStubElementType elementType, final String name,
        DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }
}
