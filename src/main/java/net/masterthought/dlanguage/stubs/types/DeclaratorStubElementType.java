package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageDeclarator;
import net.masterthought.dlanguage.psi.impl.named.DLanguageDeclaratorImpl;
import net.masterthought.dlanguage.stubs.DlangDeclaratorStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DeclaratorStubElementType extends DNamedStubElementType<DlangDeclaratorStub, DLanguageDeclarator> {
    public DeclaratorStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageDeclarator createPsi(@NotNull final DlangDeclaratorStub stub) {
        return new DLanguageDeclaratorImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangDeclaratorStub createStub(@NotNull final DLanguageDeclarator psi, final StubElement parentStub) {
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
