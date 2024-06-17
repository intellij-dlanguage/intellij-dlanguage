package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.named.DLanguageIdentifierInitializer;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageIdentifierInitializerImpl;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.stubs.DLanguageIdentifierInitializerStub;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

public class IdentifierInitializerStubElementType extends
    DNamedStubElementType<DLanguageIdentifierInitializerStub, DLanguageIdentifierInitializer> {
    public IdentifierInitializerStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageIdentifierInitializer createPsi(@NotNull final DLanguageIdentifierInitializerStub stub) {
        return new DLanguageIdentifierInitializerImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageIdentifierInitializerStub createStub(@NotNull final DLanguageIdentifierInitializer psi,
                                                         final StubElement parentStub) {
        return new DLanguageIdentifierInitializerStub(parentStub, this, psi.getName(), psi.getAttributes());
    }

    @Override
    public void serialize(@NotNull final DLanguageIdentifierInitializerStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        stub.getAttributes().write(dataStream);
    }

    @NotNull
    @Override
    public DLanguageIdentifierInitializerStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DLanguageIdentifierInitializerStub(parentStub, this, dataStream.readName(),
            DAttributes.Companion.read(dataStream));
    }
}
