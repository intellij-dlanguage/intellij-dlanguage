package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.named.DlangClassDeclaration;
import io.github.intellij.dlanguage.psi.impl.named.DlangClassDeclarationImpl;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.stubs.DlangClassDeclarationStub;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public class ClassDeclarationStubElementType extends DNamedStubElementType<DlangClassDeclarationStub, DlangClassDeclaration> {
    public ClassDeclarationStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DlangClassDeclaration createPsi(@NotNull final DlangClassDeclarationStub stub) {
        return new DlangClassDeclarationImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangClassDeclarationStub createStub(@NotNull final DlangClassDeclaration psi, final StubElement parentStub) {
        return new DlangClassDeclarationStub(parentStub, this, psi.getName(), psi.getAttributes());
    }

    @Override
    public void serialize(@NotNull final DlangClassDeclarationStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        stub.getAttributes().write(dataStream);
    }

    @NotNull
    @Override
    public DlangClassDeclarationStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangClassDeclarationStub(parentStub, this, dataStream.readName(),
            DAttributes.Companion.read(dataStream));
    }
}
