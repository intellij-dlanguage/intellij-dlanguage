package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import net.masterthought.dlanguage.psi.DLanguageClassDeclaration;
import net.masterthought.dlanguage.psi.DLanguageConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Created by franc on 1/14/2017.
 */
public class DLanguageConstructorStub extends NamedStubBase<DLanguageConstructor> {
    public DLanguageConstructorStub(StubElement parent, @NotNull IStubElementType elementType, StringRef name) {
        super(parent, elementType, name);
    }

    public DLanguageConstructorStub(StubElement parent, @NotNull IStubElementType elementType, String name) {
        super(parent, elementType, name);
    }
}
