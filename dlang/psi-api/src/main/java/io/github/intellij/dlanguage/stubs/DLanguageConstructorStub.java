package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.named.DLanguageConstructor;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import org.jetbrains.annotations.NotNull;

/**
 * Created by franc on 1/14/2017.
 */
public class DLanguageConstructorStub extends DNamedStubBase<DLanguageConstructor> {

    public DLanguageConstructorStub(final StubElement parent,
                                    @NotNull final IStubElementType elementType, final StringRef name,
                                    DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }

    public DLanguageConstructorStub(final StubElement parent,
                                    @NotNull final IStubElementType elementType, final String name,
                                    DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }
}
