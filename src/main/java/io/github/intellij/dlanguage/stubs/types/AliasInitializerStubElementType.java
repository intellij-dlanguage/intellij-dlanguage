package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageAliasInitializerImpl;
import io.github.intellij.dlanguage.stubs.DlangAliasInitializerStub;
import io.github.intellij.dlanguage.psi.DLanguageAliasInitializer;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageAliasInitializerImpl;
import io.github.intellij.dlanguage.stubs.DlangAliasInitializerStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class AliasInitializerStubElementType extends DNamedStubElementType<DlangAliasInitializerStub, DLanguageAliasInitializer> {
    public AliasInitializerStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageAliasInitializer createPsi(@NotNull final DlangAliasInitializerStub stub) {
        return new DLanguageAliasInitializerImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangAliasInitializerStub createStub(@NotNull final DLanguageAliasInitializer psi, final StubElement parentStub) {
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
