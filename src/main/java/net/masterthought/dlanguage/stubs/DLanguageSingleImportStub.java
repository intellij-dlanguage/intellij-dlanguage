package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import net.masterthought.dlanguage.psi.DLanguageSingleImport;

import java.util.HashSet;
import java.util.Set;

public class DLanguageSingleImportStub extends NamedStubBase<DLanguageSingleImport> {
    private final boolean isPublic;
    private final int numBinds;
    private final Set<String> binds = new HashSet<>();
    private final String importedModule;
    private final boolean hasName;
    private final String importName;

    public DLanguageSingleImportStub(final StubElement parent, final IStubElementType elementType, final String name, final boolean isPublic, int numBinds, Set<StringRef> binds, String importedModule, boolean hasName, String importName) {
        super(parent, elementType, name);
        this.isPublic = isPublic;
        this.numBinds = numBinds;
        for (StringRef bind : binds) {
            this.binds.add(bind.getString());
        }
        this.importedModule = importedModule;
        this.hasName = hasName;
        this.importName = importName;
    }

    public DLanguageSingleImportStub(final StubElement parent, final IStubElementType elementType, final StringRef name, final boolean isPublic, int numBinds, Set<StringRef> binds, StringRef importedModule, boolean hasName, StringRef importName) {
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

    public String getImportedModule() {
        return importedModule;
    }

    public boolean hasName() {
        return hasName;
    }

    public String getName() {
        return importName;
    }
}
