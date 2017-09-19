package io.github.intellij.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.DlangIdentifier;
import io.github.intellij.dlanguage.psi.impl.named.DlangIdentifierImpl;
import io.github.intellij.dlanguage.psi.DlangIdentifier;
import io.github.intellij.dlanguage.psi.impl.named.DlangIdentifierImpl;
import io.github.intellij.dlanguage.stubs.DlangIdentifierStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class IdentifierStubElementType extends DNamedStubElementType<DlangIdentifierStub, DlangIdentifier> {
    public IdentifierStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DlangIdentifier createPsi(@NotNull final DlangIdentifierStub stub) {
        return new DlangIdentifierImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangIdentifierStub createStub(@NotNull final DlangIdentifier psi, final StubElement parentStub) {
        return new DlangIdentifierStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DlangIdentifierStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @Override
    public boolean shouldCreateStub(final ASTNode node) {
        return false;
    }

    @NotNull
    @Override
    public DlangIdentifierStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangIdentifierStub(parentStub, this, dataStream.readName());
    }
}

