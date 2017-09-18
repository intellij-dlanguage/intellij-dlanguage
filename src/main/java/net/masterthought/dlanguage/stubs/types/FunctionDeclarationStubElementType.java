package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageFunctionDeclaration;
import net.masterthought.dlanguage.psi.impl.named.DLanguageFunctionDeclarationImpl;
import net.masterthought.dlanguage.stubs.DlangFunctionDeclarationStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class FunctionDeclarationStubElementType extends DNamedStubElementType<DlangFunctionDeclarationStub, DLanguageFunctionDeclaration> {
    public FunctionDeclarationStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageFunctionDeclaration createPsi(@NotNull final DlangFunctionDeclarationStub stub) {
        return new DLanguageFunctionDeclarationImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangFunctionDeclarationStub createStub(@NotNull final DLanguageFunctionDeclaration psi, final StubElement parentStub) {
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
