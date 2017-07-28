package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageSingleImport;
import net.masterthought.dlanguage.psi.impl.named.DLanguageSingleImportImpl;
import net.masterthought.dlanguage.stubs.DLanguageSingleImportStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Created by francis on 3/15/2017.
 */
public class SingleImportStubElementType extends DNamedStubElementType<DLanguageSingleImportStub, DLanguageSingleImport> {
    public SingleImportStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageSingleImport createPsi(@NotNull final DLanguageSingleImportStub stub) {
        return new DLanguageSingleImportImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageSingleImportStub createStub(@NotNull final DLanguageSingleImport psi, final StubElement parentStub) {
        return new DLanguageSingleImportStub(parentStub, this, psi.getName(), ((DLanguageSingleImportImpl) psi).isPublic());
    }

    @Override
    public void serialize(@NotNull final DLanguageSingleImportStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        dataStream.writeBoolean(stub.isPublic());
    }

    @NotNull
    @Override
    public DLanguageSingleImportStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DLanguageSingleImportStub(parentStub, this, dataStream.readName(), dataStream.readBoolean());
    }
}
