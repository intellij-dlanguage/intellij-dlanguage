package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.DlangAliasInitializer;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageAliasInitializerImpl;
import io.github.intellij.dlanguage.stubs.DlangAliasInitializerStub;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public class AliasInitializerStubElementType extends
    DNamedStubElementType<DlangAliasInitializerStub, DlangAliasInitializer> {
    public AliasInitializerStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DlangAliasInitializer createPsi(@NotNull final DlangAliasInitializerStub stub) {
        return new DLanguageAliasInitializerImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangAliasInitializerStub createStub(@NotNull final DlangAliasInitializer psi,
        final StubElement parentStub) {
        return new DlangAliasInitializerStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DlangAliasInitializerStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DlangAliasInitializerStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangAliasInitializerStub(parentStub, this, dataStream.readName());
    }
}
