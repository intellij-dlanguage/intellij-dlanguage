package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import net.masterthought.dlanguage.psi.DLanguageConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Created by franc on 1/14/2017.
 */
public class DlangConstructorStub extends NamedStubBase<DLanguageConstructor> {
    public DlangConstructorStub(final StubElement parent, @NotNull final IStubElementType elementType, final StringRef name) {
        super(parent, elementType, name);
    }

    public DlangConstructorStub(final StubElement parent, @NotNull final IStubElementType elementType, final String name) {
        super(parent, elementType, name);
    }
}
