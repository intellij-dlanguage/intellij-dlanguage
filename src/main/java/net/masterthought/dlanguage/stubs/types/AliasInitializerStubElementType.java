package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageAliasInitializer;
import net.masterthought.dlanguage.psi.impl.named.DLanguageAliasInitializerImpl;
import net.masterthought.dlanguage.stubs.DLanguageAliasInitializerStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class AliasInitializerStubElementType extends DNamedStubElementType<DLanguageAliasInitializerStub, DLanguageAliasInitializer> {
    public AliasInitializerStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageAliasInitializer createPsi(@NotNull final DLanguageAliasInitializerStub stub) {
        return new DLanguageAliasInitializerImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageAliasInitializerStub createStub(@NotNull final DLanguageAliasInitializer psi, final StubElement parentStub) {
        return new DLanguageAliasInitializerStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DLanguageAliasInitializerStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageAliasInitializerStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DLanguageAliasInitializerStub(parentStub, this, dataStream.readName());
    }
}
