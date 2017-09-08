package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageInterfaceOrClass;
import net.masterthought.dlanguage.psi.impl.named.DLanguageInterfaceOrClassImpl;
import net.masterthought.dlanguage.stubs.DLanguageInterfaceOrClassStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class InterfaceOrClassStubElementType extends DNamedStubElementType<DLanguageInterfaceOrClassStub, DLanguageInterfaceOrClass> {
    public InterfaceOrClassStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageInterfaceOrClass createPsi(@NotNull final DLanguageInterfaceOrClassStub stub) {
        return new DLanguageInterfaceOrClassImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageInterfaceOrClassStub createStub(@NotNull final DLanguageInterfaceOrClass psi, final StubElement parentStub) {
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
