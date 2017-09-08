package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageFunctionDeclaration;
import net.masterthought.dlanguage.psi.impl.named.DLanguageFunctionDeclarationImpl;
import net.masterthought.dlanguage.stubs.DLanguageFunctionDeclarationStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class FunctionDeclarationStubElementType extends DNamedStubElementType<DLanguageFunctionDeclarationStub, DLanguageFunctionDeclaration> {
    public FunctionDeclarationStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageFunctionDeclaration createPsi(@NotNull final DLanguageFunctionDeclarationStub stub) {
        return new DLanguageFunctionDeclarationImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageFunctionDeclarationStub createStub(@NotNull final DLanguageFunctionDeclaration psi, final StubElement parentStub) {
        return new DLanguageFunctionDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DLanguageFunctionDeclarationStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageFunctionDeclarationStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DLanguageFunctionDeclarationStub(parentStub, this, dataStream.readName());
    }
}
