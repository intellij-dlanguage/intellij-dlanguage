package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageAutoAssignmentImpl;
import io.github.intellij.dlanguage.psi.named.DLanguageAutoAssignment;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.stubs.DLanguageAutoAssignmentStub;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public class AutoAssignmentStubElementType extends
    DNamedStubElementType<DLanguageAutoAssignmentStub, DLanguageAutoAssignment> {
    public AutoAssignmentStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageAutoAssignment createPsi(@NotNull final DLanguageAutoAssignmentStub stub) {
        return new DLanguageAutoAssignmentImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageAutoAssignmentStub createStub(@NotNull final DLanguageAutoAssignment psi,
                                                  final StubElement parentStub) {
        return new DLanguageAutoAssignmentStub(parentStub, this, psi.getName(),
            psi.getAttributes());
    }

    @Override
    public void serialize(@NotNull final DLanguageAutoAssignmentStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        stub.getAttributes().write(dataStream);
    }

    @NotNull
    @Override
    public DLanguageAutoAssignmentStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DLanguageAutoAssignmentStub(parentStub, this, dataStream.readName(),
            DAttributes.Companion.read(dataStream));
    }
}
