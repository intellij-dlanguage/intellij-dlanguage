package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageIdentifier;
import net.masterthought.dlanguage.psi.impl.named.DLanguageIdentifierImpl;
import net.masterthought.dlanguage.stubs.DLanguageIdentifierStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class IdentifierStubElementType extends DNamedStubElementType<DLanguageIdentifierStub, DLanguageIdentifier> {
    public IdentifierStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageIdentifier createPsi(@NotNull final DLanguageIdentifierStub stub) {
        return new DLanguageIdentifierImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageIdentifierStub createStub(@NotNull final DLanguageIdentifier psi, final StubElement parentStub) {
        return new DLanguageIdentifierStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DLanguageIdentifierStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @Override
    public boolean shouldCreateStub(final ASTNode node) {
        return false;
    }

    @NotNull
    @Override
    public DLanguageIdentifierStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DLanguageIdentifierStub(parentStub, this, dataStream.readName());
    }
}

