package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.DlangTemplateDeclaration;
import io.github.intellij.dlanguage.psi.impl.named.DlangTemplateDeclarationImpl;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.stubs.DlangTemplateDeclarationStub;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public class DlangTemplateDeclarationStubElementType extends DNamedStubElementType<DlangTemplateDeclarationStub, DlangTemplateDeclaration> {
    public DlangTemplateDeclarationStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DlangTemplateDeclaration createPsi(@NotNull final DlangTemplateDeclarationStub stub) {
        return new DlangTemplateDeclarationImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangTemplateDeclarationStub createStub(@NotNull final DlangTemplateDeclaration psi, final StubElement parentStub) {
        return new DlangTemplateDeclarationStub(parentStub, this, psi.getName(),
            psi.getAttributes());
    }

    @Override
    public void serialize(@NotNull final DlangTemplateDeclarationStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        stub.getAttributes().write(dataStream);
    }

    @NotNull
    @Override
    public DlangTemplateDeclarationStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangTemplateDeclarationStub(parentStub, this, dataStream.readName(),
            DAttributes.Companion.read(dataStream));
    }
}
