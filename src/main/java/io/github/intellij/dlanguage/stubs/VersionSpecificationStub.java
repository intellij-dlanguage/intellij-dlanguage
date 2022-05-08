package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.named.DlangVersionSpecification;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis on 1/5/2018.
 */
public class VersionSpecificationStub extends DNamedStubBase<DlangVersionSpecification> {

    protected VersionSpecificationStub(final StubElement parent,
        @NotNull final IStubElementType elementType,
        final StringRef name,
        final DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }

    public VersionSpecificationStub(final StubElement parent,
        @NotNull final IStubElementType elementType, final String name,
        final DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }
}
