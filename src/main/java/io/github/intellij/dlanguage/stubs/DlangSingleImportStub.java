package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.named.DlangSingleImport;
import io.github.intellij.dlanguage.psi.references.DReference;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DlangSingleImportStub extends DNamedStubBase<DlangSingleImport> {

    private final int numBinds;
    private final Set<String> binds = new HashSet<>();
    @NotNull
    private final String importedModule;
    private final boolean hasName;
    private final String importName;

    public DlangSingleImportStub(final StubElement parent,
                                 @NotNull final IStubElementType elementType,
                                 @Nullable final String name,
                                 final int numBinds,
                                 final Set<StringRef> binds,
                                 final @NotNull String importedModule,
                                 final boolean hasName,
                                 final String importName,
                                 final DAttributes attributes) {
        super(parent, elementType, name, attributes);
        this.numBinds = numBinds;
        for (final StringRef bind : binds) {
            this.binds.add(bind.getString());
        }
        this.importedModule = importedModule;
        assert !DReference.Companion.getNAME_NOT_FOUND_STRING().equals(importedModule) : "importedModule should not be blank";
        this.hasName = hasName;
        this.importName = importName;
    }

    public DlangSingleImportStub(final StubElement parent,
                                 @NotNull final IStubElementType elementType,
                                 @Nullable final StringRef name,
                                 final int numBinds,
                                 final Set<StringRef> binds,
                                 final @NotNull StringRef importName,
                                 final boolean hasName,
                                 final StringRef importedModule,
                                 final DAttributes attributes) {
        this(parent, elementType, name != null ? name.getString() : null,
            numBinds, binds, importedModule.getString(), hasName,
            importName.getString(), attributes);
    }

    public int numBinds() {
        return numBinds;
    }

    public Set<String> getBinds() {
        return binds;
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
}
