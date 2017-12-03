package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.DlangStructDeclaration;
import io.github.intellij.dlanguage.psi.impl.named.DlangStructDeclarationImpl;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.stubs.DlangStructDeclarationStub;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public class StructDeclarationStubElementType extends DNamedStubElementType<DlangStructDeclarationStub, DlangStructDeclaration> {
    public StructDeclarationStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DlangStructDeclaration createPsi(@NotNull final DlangStructDeclarationStub stub) {
        return new DlangStructDeclarationImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangStructDeclarationStub createStub(@NotNull final DlangStructDeclaration psi, final StubElement parentStub) {
        return new DlangStructDeclarationStub(parentStub, this, psi.getName(), psi.getAttributes());
    }

    @Override
    public void serialize(@NotNull final DlangStructDeclarationStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        stub.getAttributes().write(dataStream);
    }

    @NotNull
    @Override
    public DlangStructDeclarationStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangStructDeclarationStub(parentStub, this, dataStream.readName(),
            DAttributes.Companion.read(dataStream));
    }
}
