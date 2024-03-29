package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.named.DlangVersionSpecification;
import io.github.intellij.dlanguage.psi.impl.named.DlangVersionSpecificationImpl;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.stubs.VersionSpecificationStub;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis on 1/5/2018.
 */
public class VersionSpecificationElementType extends
    DNamedStubElementType<VersionSpecificationStub, DlangVersionSpecification> {

    public VersionSpecificationElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DlangVersionSpecification createPsi(@NotNull final VersionSpecificationStub stub) {
        return new DlangVersionSpecificationImpl(stub, this);
    }

    @NotNull
    @Override
    public VersionSpecificationStub createStub(@NotNull final DlangVersionSpecification psi,
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
