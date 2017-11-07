package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.DlangSingleImport;
import io.github.intellij.dlanguage.psi.DlangSingleImport;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class DlangSingleImportStub extends NamedStubBase<DlangSingleImport> {

    private final boolean isPublic;
    private final int numBinds;
    private final Set<String> binds = new HashSet<>();
    private final @NotNull
    String importedModule;
    private final boolean hasName;
    private final String importName;

    public DlangSingleImportStub(final StubElement parent, final IStubElementType elementType, final String name, final boolean isPublic, final int numBinds, final Set<StringRef> binds, final @NotNull String importedModule, final boolean hasName, final String importName) {
        super(parent, elementType, name);
        this.isPublic = isPublic;
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

    public DlangSingleImportStub(final StubElement parent, final IStubElementType elementType, final StringRef name, final boolean isPublic, final int numBinds, final Set<StringRef> binds, final @NotNull StringRef importedModule, final boolean hasName, final StringRef importName) {
        this(parent, elementType, name.getString(), isPublic, numBinds, binds, importedModule.getString(), hasName, importName.getString());
    }

    public boolean isPublic() {
        return isPublic;
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
