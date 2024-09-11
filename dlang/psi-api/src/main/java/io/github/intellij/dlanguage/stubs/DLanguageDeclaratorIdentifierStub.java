package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.named.DLanguageDeclaratorIdentifier;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DLanguageDeclaratorIdentifierStub extends DNamedStubBase<DLanguageDeclaratorIdentifier> {
    public DLanguageDeclaratorIdentifierStub(StubElement parent, @NotNull IStubElementType elementType, @Nullable StringRef name, DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }
    public DLanguageDeclaratorIdentifierStub(StubElement parent, @NotNull IStubElementType elementType, @Nullable String name, DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }
}
