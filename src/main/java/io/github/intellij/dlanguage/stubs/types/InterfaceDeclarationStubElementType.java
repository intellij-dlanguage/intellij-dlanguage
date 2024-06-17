package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.impl.named.DlangClassDeclarationImpl;
import io.github.intellij.dlanguage.psi.impl.named.DlangInterfaceDeclarationImpl;
import io.github.intellij.dlanguage.psi.named.DlangClassDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangInterfaceDeclaration;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.stubs.DlangClassDeclarationStub;
import io.github.intellij.dlanguage.stubs.DlangInterfaceDeclarationStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class InterfaceDeclarationStubElementType extends DNamedStubElementType<DlangInterfaceDeclarationStub, DlangInterfaceDeclaration> {
    public InterfaceDeclarationStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DlangInterfaceDeclaration createPsi(@NotNull final DlangInterfaceDeclarationStub stub) {
        return new DlangInterfaceDeclarationImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangInterfaceDeclarationStub createStub(@NotNull final DlangInterfaceDeclaration psi, final StubElement parentStub) {
        return new DlangInterfaceDeclarationStub(parentStub, this, psi.getName(), psi.getAttributes());
    }

    @Override
    public void serialize(@NotNull final DlangInterfaceDeclarationStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        stub.getAttributes().write(dataStream);
    }

    @NotNull
    @Override
    public DlangInterfaceDeclarationStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangInterfaceDeclarationStub(parentStub, this, dataStream.readName(),
            DAttributes.Companion.read(dataStream));
    }
}
