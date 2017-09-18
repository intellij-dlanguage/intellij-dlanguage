package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DlangInterfaceOrClass;
import net.masterthought.dlanguage.psi.impl.named.DlangInterfaceOrClassImpl;
import net.masterthought.dlanguage.stubs.DLanguageInterfaceOrClassStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class InterfaceOrClassStubElementType extends DNamedStubElementType<DLanguageInterfaceOrClassStub, DlangInterfaceOrClass> {
    public InterfaceOrClassStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DlangInterfaceOrClass createPsi(@NotNull final DLanguageInterfaceOrClassStub stub) {
        return new DlangInterfaceOrClassImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageInterfaceOrClassStub createStub(@NotNull final DlangInterfaceOrClass psi, final StubElement parentStub) {
        return new DLanguageInterfaceOrClassStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DLanguageInterfaceOrClassStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageInterfaceOrClassStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DLanguageInterfaceOrClassStub(parentStub, this, dataStream.readName());
    }
}
