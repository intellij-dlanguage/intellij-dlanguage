package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.named.DLanguageSingleImport;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DLanguageSingleImportStub extends DNamedStubBase<DLanguageSingleImport> {

    private final int numBinds;
    private final Set<String> binds = new HashSet<>();

    private final int numNamedBinds;
    private final Map<String, String> namedBinds = new HashMap<>();

    private final String importedModule;
    private final boolean hasName;
    private final String importName;

    public DLanguageSingleImportStub(final StubElement parent,
                                     @NotNull final IStubElementType elementType,
                                     @Nullable final String name,
                                     final int numBinds,
                                     final Set<StringRef> binds,
                                     final int numNamedBinds,
                                     final Map<StringRef, StringRef> namedBinds,
                                     final @Nullable String importedModule,
                                     final boolean hasName,
                                     final String importName,
                                     final DAttributes attributes) {
        super(parent, elementType, name, attributes);
        this.numBinds = numBinds;
        for (final StringRef bind : binds) {
            this.binds.add(bind.getString());
        }
        this.numNamedBinds = numNamedBinds;
        namedBinds.forEach((bindName, bind) -> this.namedBinds.put(bindName.getString(), bind.getString()));
        this.importedModule = importedModule;
        assert importedModule == null || !importedModule.isBlank() : "imported Module should not be blank";
        this.hasName = hasName;
        this.importName = importName;
    }

    public DLanguageSingleImportStub(final StubElement parent,
                                     @NotNull final IStubElementType elementType,
                                     @Nullable final StringRef name,
                                     final int numBinds,
                                     final Set<StringRef> binds,
                                     final int numNamedBinds,
                                     final Map<StringRef, StringRef> namedBinds,
                                     final @Nullable StringRef importName,
                                     final boolean hasName,
                                     final StringRef importedModule,
                                     final DAttributes attributes) {
        this(parent, elementType, name != null ? name.getString() : null,
            numBinds, binds, numNamedBinds, namedBinds, importedModule.getString(), hasName,
            importName == null ? null : importName.getString(),
            attributes);
    }

    public int numBinds() {
        return numBinds;
    }

    public Set<String> getBinds() {
        return binds;
    }

    public int numNamedBinds() {
        return numNamedBinds;
    }
    public Map<String, String> getNamedBinds() {
        return namedBinds;
    }

    @NotNull
    public String getImportedModule() {
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

    public Map<String, String> getApplicableNamedImportBinds() {
        return namedBinds;
    }
}
