package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.named.DlangInterfaceOrClass;
import io.github.intellij.dlanguage.psi.impl.named.DlangInterfaceOrClassImpl;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.stubs.DlangInterfaceOrClassStub;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public class InterfaceOrClassStubElementType extends DNamedStubElementType<DlangInterfaceOrClassStub, DlangInterfaceOrClass> {
    public InterfaceOrClassStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DlangInterfaceOrClass createPsi(@NotNull final DlangInterfaceOrClassStub stub) {
        return new DlangInterfaceOrClassImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangInterfaceOrClassStub createStub(@NotNull final DlangInterfaceOrClass psi, final StubElement parentStub) {
        return new DlangInterfaceOrClassStub(parentStub, this, psi.getName(), psi.getAttributes());
    }

    @Override
    public void serialize(@NotNull final DlangInterfaceOrClassStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        stub.getAttributes().write(dataStream);
    }

    @NotNull
    @Override
    public DlangInterfaceOrClassStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangInterfaceOrClassStub(parentStub, this, dataStream.readName(),
            DAttributes.Companion.read(dataStream));
    }
}
