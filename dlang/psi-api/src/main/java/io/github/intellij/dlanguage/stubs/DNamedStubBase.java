package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.NamedStubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by francis on 12/3/2017.
 */
public class DNamedStubBase<T extends DNamedElement> extends NamedStubBase<T> {

    private final DAttributes attributes;

    protected DNamedStubBase(final StubElement parent,
                             @NotNull final IStubElementType elementType,
                             @Nullable final StringRef name,
                             final DAttributes attributes) {
        super(parent, elementType, name);
        this.attributes = attributes;
    }

    protected DNamedStubBase(final StubElement parent,
                             @NotNull final IStubElementType elementType,
                             @Nullable final String name,
                             final DAttributes attributes) {
        super(parent, elementType, name);
        this.attributes = attributes;
    }

    public DAttributes getAttributes() {
        return attributes;
    }
}
