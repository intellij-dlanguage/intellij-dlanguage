package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DlangStructDeclaration;
import net.masterthought.dlanguage.psi.impl.named.DlangStructDeclarationImpl;
import net.masterthought.dlanguage.stubs.DlangStructDeclarationStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

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
        return new DlangStructDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DlangStructDeclarationStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DlangStructDeclarationStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangStructDeclarationStub(parentStub, this, dataStream.readName());
    }
}
