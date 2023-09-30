package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.named.DlangDeclaratorIdentifier;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DlangDeclaratorIdentifierStub extends DNamedStubBase<DlangDeclaratorIdentifier> {
    public DlangDeclaratorIdentifierStub(StubElement parent, @NotNull IStubElementType elementType, @Nullable StringRef name, DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }
    public DlangDeclaratorIdentifierStub(StubElement parent, @NotNull IStubElementType elementType, @Nullable String name, DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }
}
