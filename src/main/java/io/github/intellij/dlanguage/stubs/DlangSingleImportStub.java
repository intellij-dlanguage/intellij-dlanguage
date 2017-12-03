package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.DlangSingleImport;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class DlangSingleImportStub extends DNamedStubBase<DlangSingleImport> {

    private final int numBinds;
    private final Set<String> binds = new HashSet<>();
    private final @NotNull
    String importedModule;
    private final boolean hasName;
    private final String importName;

    public DlangSingleImportStub(final StubElement parent, final IStubElementType elementType,
        final String name, final int numBinds, final Set<StringRef> binds,
        final @NotNull String importedModule, final boolean hasName, final String importName,
        final DAttributes attributes) {
        super(parent, elementType, name, attributes);
        this.numBinds = numBinds;
        for (final StringRef bind : binds) {
            this.binds.add(bind.getString());
        }
        this.importedModule = importedModule;
        if (importedModule.equals(""))
            throw new IllegalStateException();
        this.hasName = hasName;
        this.importName = importName;
    }

    public DlangSingleImportStub(final StubElement parent, final IStubElementType elementType,
        final StringRef name, final int numBinds, final Set<StringRef> binds,
        final @NotNull StringRef importName, final boolean hasName, final StringRef importedModule,
        final DAttributes attributes) {
        this(parent, elementType, name.getString(), numBinds, binds, importedModule.getString(),
            hasName, importName.getString(),
            attributes);
    }

    public int numBinds() {
        return numBinds;
    }

    public Set<String> getBinds() {
        return binds;
    }

    public @NotNull
    String getImportedModule() {
        return importedModule;
    }

    public boolean hasName() {
        return hasName;
    }

    public String getName() {
        return importName;
    }

    public Set<String> getApplicableImportBinds() {
        return binds;
    }
}
