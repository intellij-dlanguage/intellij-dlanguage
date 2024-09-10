package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import io.github.intellij.dlanguage.psi.DLanguageNamedImportBind;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis on 8/8/2017.
 */
public class DLanguageNamedImportBindStub extends DNamedStubBase<DLanguageNamedImportBind> {

    public DLanguageNamedImportBindStub(final StubElement parent,
                                        @NotNull final IStubElementType elementType, final StringRef name,
                                        DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }

    public DLanguageNamedImportBindStub(final StubElement parent,
                                        @NotNull final IStubElementType elementType, final String name,
                                        DAttributes attributes) {
        super(parent, elementType, name, attributes);
    }
}
