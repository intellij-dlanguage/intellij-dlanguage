package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.DlangAutoDeclarationPart;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageAutoDeclarationPartImpl;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.stubs.DlangAutoDeclarationPartStub;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public class AutoDeclarationPartStubElementType extends
    DNamedStubElementType<DlangAutoDeclarationPartStub, DlangAutoDeclarationPart> {
    public AutoDeclarationPartStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DlangAutoDeclarationPart createPsi(@NotNull final DlangAutoDeclarationPartStub stub) {
        return new DLanguageAutoDeclarationPartImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangAutoDeclarationPartStub createStub(@NotNull final DlangAutoDeclarationPart psi,
        final StubElement parentStub) {
        return new DlangAutoDeclarationPartStub(parentStub, this, psi.getName(),
            psi.getAttributes());
    }

    @Override
    public void serialize(@NotNull final DlangAutoDeclarationPartStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        stub.getAttributes().write(dataStream);
    }

    @NotNull
    @Override
    public DlangAutoDeclarationPartStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangAutoDeclarationPartStub(parentStub, this, dataStream.readName(),
            DAttributes.Companion.read(dataStream));
    }
}
