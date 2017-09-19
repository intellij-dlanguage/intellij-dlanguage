package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.DlangSingleImport;
import io.github.intellij.dlanguage.psi.impl.named.DlangSingleImportImpl;
import io.github.intellij.dlanguage.stubs.DlangSingleImportStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by francis on 3/15/2017.
 */
public class SingleImportStubElementType extends DNamedStubElementType<DlangSingleImportStub, DlangSingleImport> {

    public SingleImportStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DlangSingleImport createPsi(@NotNull final DlangSingleImportStub stub) {
        return new DlangSingleImportImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangSingleImportStub createStub(@NotNull final DlangSingleImport psi, final StubElement parentStub) {
        final Set<StringRef> binds = new HashSet<>();
        for (final String bind : psi.getApplicableImportBinds()) {
            binds.add(StringRef.fromString(bind));
        }
        return new DlangSingleImportStub(parentStub, this, psi.getName(), ((DlangSingleImportImpl) psi).isPublic(), psi.getApplicableImportBinds().size(), binds, psi.getImportedModuleName(), psi.getIdentifier() != null, psi.getIdentifier() != null ? psi.getIdentifier().getName() : "");
    }

    @Override
    public void serialize(@NotNull final DlangSingleImportStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        dataStream.writeBoolean(stub.isPublic());
        dataStream.writeInt(stub.numBinds());
        for (final String s : stub.getBinds()) {
            dataStream.writeName(s);
        }
        dataStream.writeName(stub.getName());
        dataStream.writeBoolean(stub.hasName());
        dataStream.writeName(stub.getImportedModule());
    }

    @NotNull
    @Override
    public DlangSingleImportStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        final StringRef name = dataStream.readName();
        final boolean isPublic = dataStream.readBoolean();
        final int numBinds = dataStream.readInt();
        final Set<StringRef> binds = new HashSet<>();
        for (int i = 0; i < numBinds; i++) {
            binds.add(dataStream.readName());
        }
        final StringRef importedModule = dataStream.readName();
        final boolean hasName = dataStream.readBoolean();
        final StringRef importName = dataStream.readName();
        return new DlangSingleImportStub(parentStub, this, name, isPublic, numBinds, binds, importedModule, hasName, importName);
    }
}
