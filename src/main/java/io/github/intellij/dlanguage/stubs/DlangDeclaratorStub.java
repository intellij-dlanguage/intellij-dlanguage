package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.DlangDeclarator;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis nixon on 1/11/2017.
 */
public class DlangDeclaratorStub extends NamedStubBase<DlangDeclarator> {
    public DlangDeclaratorStub(final StubElement parent, @NotNull final IStubElementType elementType, final StringRef name) {
        super(parent, elementType, name);
    }

    public DlangDeclaratorStub(final StubElement parent, final IStubElementType elementType, final String name) {
        super(parent, elementType, name);
    }
}
