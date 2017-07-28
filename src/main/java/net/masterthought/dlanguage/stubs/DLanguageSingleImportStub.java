package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import net.masterthought.dlanguage.psi.DLanguageSingleImport;

public class DLanguageSingleImportStub extends NamedStubBase<DLanguageSingleImport> {
    private final boolean isPublic;

    public DLanguageSingleImportStub(final StubElement parent, final IStubElementType elementType, final StringRef name, final boolean isPublic) {
        super(parent, elementType, name);
        this.isPublic = isPublic;
    }

    public DLanguageSingleImportStub(final StubElement parent, final IStubElementType elementType, final String name, final boolean isPublic) {
        super(parent, elementType, name);
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
