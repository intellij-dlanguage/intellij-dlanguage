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
    public IdentifierStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageIdentifier createPsi(@NotNull DLanguageIdentifierStub stub) {
        return new DLanguageIdentifierImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageIdentifierStub createStub(@NotNull DLanguageIdentifier psi, StubElement parentStub) {
        return new DLanguageIdentifierStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageIdentifierStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return false;
    }

    @NotNull
    @Override
    public DLanguageIdentifierStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageIdentifierStub(parentStub, this, dataStream.readName());
    }
}

