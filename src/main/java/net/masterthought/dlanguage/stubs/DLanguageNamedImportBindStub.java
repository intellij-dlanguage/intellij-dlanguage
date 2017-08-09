package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import net.masterthought.dlanguage.psi.DLanguageNamedImportBind;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis on 8/8/2017.
 */
public class DLanguageNamedImportBindStub extends NamedStubBase<DLanguageNamedImportBind> {
    public DLanguageNamedImportBindStub(StubElement parent, @NotNull IStubElementType elementType, StringRef name) {
        super(parent, elementType, name);
    }

    public DLanguageNamedImportBindStub(StubElement parent, @NotNull IStubElementType elementType, String name) {
        super(parent, elementType, name);
    }
}
