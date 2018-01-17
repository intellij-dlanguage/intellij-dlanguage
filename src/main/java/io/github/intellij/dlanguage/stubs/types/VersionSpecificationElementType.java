package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.named.DLanguageVersionSpecification;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageVersionSpecificationImpl;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.stubs.VersionSpecificationStub;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis on 1/5/2018.
 */
public class VersionSpecificationElementType extends
    DNamedStubElementType<VersionSpecificationStub, DLanguageVersionSpecification> {

    public VersionSpecificationElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageVersionSpecification createPsi(@NotNull final VersionSpecificationStub stub) {
        return new DLanguageVersionSpecificationImpl(stub, this);
    }

    @NotNull
    @Override
    public VersionSpecificationStub createStub(@NotNull final DLanguageVersionSpecification psi,
        final StubElement parentStub) {
        return new VersionSpecificationStub(parentStub, this, psi.getName(), psi.getAttributes());
    }

    @NotNull
    @Override
    public VersionSpecificationStub deserialize(@NotNull final StubInputStream dataStream,
        final StubElement parentStub) throws IOException {
        final StringRef name = dataStream.readName();
        final DAttributes attributes = DAttributes.Companion.read(dataStream);
        return new VersionSpecificationStub(parentStub, this, name.getString(), attributes);

    }

    @Override
    public void serialize(@NotNull final VersionSpecificationStub stub,
        @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        stub.getAttributes().write(dataStream);
    }
}
