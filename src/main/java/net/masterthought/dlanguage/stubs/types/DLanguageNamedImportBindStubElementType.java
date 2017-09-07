package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageNamedImportBind;
import net.masterthought.dlanguage.psi.impl.named.DLanguageNamedImportBindImpl;
import net.masterthought.dlanguage.stubs.DLanguageNamedImportBindStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageNamedImportBindStubElementType extends DNamedStubElementType<DLanguageNamedImportBindStub, DLanguageNamedImportBind> {
    public DLanguageNamedImportBindStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageNamedImportBind createPsi(@NotNull final DLanguageNamedImportBindStub stub) {
        return new DLanguageNamedImportBindImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageNamedImportBindStub createStub(@NotNull final DLanguageNamedImportBind psi, final StubElement parentStub) {
        return new DLanguageNamedImportBindStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DLanguageNamedImportBindStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageNamedImportBindStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DLanguageNamedImportBindStub(parentStub, this, dataStream.readName());
    }
}
