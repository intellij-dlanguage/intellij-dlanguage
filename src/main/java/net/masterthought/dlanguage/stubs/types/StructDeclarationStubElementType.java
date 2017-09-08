package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageStructDeclaration;
import net.masterthought.dlanguage.psi.impl.named.DLanguageStructDeclarationImpl;
import net.masterthought.dlanguage.stubs.DLanguageStructDeclarationStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class StructDeclarationStubElementType extends DNamedStubElementType<DLanguageStructDeclarationStub, DLanguageStructDeclaration> {
    public StructDeclarationStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageStructDeclaration createPsi(@NotNull final DLanguageStructDeclarationStub stub) {
        return new DLanguageStructDeclarationImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageStructDeclarationStub createStub(@NotNull final DLanguageStructDeclaration psi, final StubElement parentStub) {
        return new DLanguageStructDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DLanguageStructDeclarationStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageStructDeclarationStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DLanguageStructDeclarationStub(parentStub, this, dataStream.readName());
    }
}
