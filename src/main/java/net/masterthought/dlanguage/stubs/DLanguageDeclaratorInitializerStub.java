package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import net.masterthought.dlanguage.psi.DLanguageDeclaratorInitializer;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis nixon on 1/11/2017.
 */
public class DLanguageDeclaratorInitializerStub extends NamedStubBase<DLanguageDeclaratorInitializer> {
    public DLanguageDeclaratorInitializerStub(StubElement parent, @NotNull IStubElementType elementType, StringRef name) {
        super(parent, elementType, name);
    }

    public DLanguageDeclaratorInitializerStub(StubElement parent, IStubElementType elementType, String name) {
        super(parent, elementType, name);
    }
}
