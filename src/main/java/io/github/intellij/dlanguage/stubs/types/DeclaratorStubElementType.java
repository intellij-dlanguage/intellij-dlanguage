package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.DlangDeclarator;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageDeclaratorImpl;
import io.github.intellij.dlanguage.stubs.DlangDeclaratorStub;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public class DeclaratorStubElementType extends
    DNamedStubElementType<DlangDeclaratorStub, DlangDeclarator> {
    public DeclaratorStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DlangDeclarator createPsi(@NotNull final DlangDeclaratorStub stub) {
        return new DLanguageDeclaratorImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangDeclaratorStub createStub(@NotNull final DlangDeclarator psi,
        final StubElement parentStub) {
        return new DlangDeclaratorStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DlangDeclaratorStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DlangDeclaratorStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangDeclaratorStub(parentStub, this, dataStream.readName());
    }
}
