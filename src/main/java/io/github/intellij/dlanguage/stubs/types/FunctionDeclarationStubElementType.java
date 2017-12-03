package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.DlangFunctionDeclaration;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageFunctionDeclarationImpl;
import io.github.intellij.dlanguage.stubs.DlangFunctionDeclarationStub;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public class FunctionDeclarationStubElementType extends
    DNamedStubElementType<DlangFunctionDeclarationStub, DlangFunctionDeclaration> {
    public FunctionDeclarationStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DlangFunctionDeclaration createPsi(@NotNull final DlangFunctionDeclarationStub stub) {
        return new DLanguageFunctionDeclarationImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangFunctionDeclarationStub createStub(@NotNull final DlangFunctionDeclaration psi,
        final StubElement parentStub) {
        return new DlangFunctionDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DlangFunctionDeclarationStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DlangFunctionDeclarationStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangFunctionDeclarationStub(parentStub, this, dataStream.readName());
    }
}
