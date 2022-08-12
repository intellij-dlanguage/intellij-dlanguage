package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.named.DlangModuleDeclaration;
import io.github.intellij.dlanguage.psi.impl.named.DlangModuleDeclarationImpl;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.stubs.DlangModuleDeclarationStub;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public class ModuleDeclarationStubElementType extends DNamedStubElementType<DlangModuleDeclarationStub, DlangModuleDeclaration> {
    public ModuleDeclarationStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DlangModuleDeclaration createPsi(@NotNull final DlangModuleDeclarationStub stub) {
        return new DlangModuleDeclarationImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangModuleDeclarationStub createStub(@NotNull final DlangModuleDeclaration psi, final StubElement parentStub) {
        return new DlangModuleDeclarationStub(parentStub, this, psi.getName(), psi.getAttributes());
    }

    @Override
    public void serialize(@NotNull final DlangModuleDeclarationStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        stub.getAttributes().write(dataStream);
    }

    @NotNull
    @Override
    public DlangModuleDeclarationStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangModuleDeclarationStub(parentStub, this, dataStream.readName(),
            DAttributes.Companion.read(dataStream));
    }
}
