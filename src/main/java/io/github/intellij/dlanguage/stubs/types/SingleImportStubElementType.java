package io.github.intellij.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.named.DlangSingleImport;
import io.github.intellij.dlanguage.psi.impl.named.DlangSingleImportImpl;
import io.github.intellij.dlanguage.psi.references.DReference;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.stubs.DlangSingleImportStub;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

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
        final Map<StringRef, StringRef> namedBinds = new HashMap<>();
        psi.getApplicableNamedImportBinds().forEach((bindName, bind) -> namedBinds.put(StringRef.fromString(bindName), StringRef.fromString(bind)));
        return new DlangSingleImportStub(parentStub, this, psi.getName(),
            psi.getApplicableImportBinds().size(), binds,
            psi.getApplicableNamedImportBinds().size(), namedBinds,
            psi.getImportedModuleName(),
            psi.getIdentifier() != null,
            psi.getIdentifier() != null ? psi.getIdentifier().getText() : "",
            psi.getAttributes());
    }

    @Override
    public void serialize(@NotNull final DlangSingleImportStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        dataStream.writeInt(stub.numBinds());
        for (final String s : stub.getBinds()) {
            dataStream.writeName(s);
        }
        dataStream.writeInt(stub.numNamedBinds());
        for (var entry : stub.getNamedBinds().entrySet()) {
            dataStream.writeName(entry.getKey());
            dataStream.writeName(entry.getValue());
        }
        dataStream.writeName(stub.getName());
        dataStream.writeBoolean(stub.hasName());
        if (stub.getImportedModule().equals(""))
            throw new IllegalStateException();
        dataStream.writeName(stub.getImportedModule());
        stub.getAttributes().write(dataStream);
    }

    @NotNull
    @Override
    public DlangSingleImportStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        final StringRef name = dataStream.readName();
        final int numBinds = dataStream.readInt();
        final Set<StringRef> binds = new HashSet<>();
        for (int i = 0; i < numBinds; i++) {
            binds.add(dataStream.readName());
        }
        final int numNamedBinds = dataStream.readInt();
        final Map<StringRef, StringRef> namedBinds = new HashMap<>();
        for (int i = 0; i < numNamedBinds; i++) {
            namedBinds.put(dataStream.readName(), dataStream.readName());
        }
        final StringRef importName = dataStream.readName();
        final boolean hasName = dataStream.readBoolean();
        final StringRef importedModule = dataStream.readName();
        final DAttributes attributes = DAttributes.Companion.read(dataStream);
        return new DlangSingleImportStub(parentStub, this, name, numBinds, binds, numNamedBinds, namedBinds,
            importName, hasName, importedModule, attributes);
    }

    @Override
    public boolean shouldCreateStub(final ASTNode node) {
        return !((DlangSingleImport) node.getPsi()).getImportedModuleName().equals(DReference.Companion.getNAME_NOT_FOUND_STRING());
    }
}
