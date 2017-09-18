package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageConstructor;
import net.masterthought.dlanguage.psi.impl.named.DLanguageConstructorImpl;
import net.masterthought.dlanguage.stubs.DlangConstructorStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ConstructorStubElementType extends DNamedStubElementType<DlangConstructorStub, DLanguageConstructor> {
    public ConstructorStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageConstructor createPsi(@NotNull final DlangConstructorStub stub) {
        return new DLanguageConstructorImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangConstructorStub createStub(@NotNull final DLanguageConstructor psi, final StubElement parentStub) {
        return new DlangConstructorStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DlangConstructorStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DlangConstructorStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangConstructorStub(parentStub, this, dataStream.readName());
    }
}
