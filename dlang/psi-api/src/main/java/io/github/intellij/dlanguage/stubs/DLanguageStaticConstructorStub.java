package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import io.github.intellij.dlanguage.psi.DLanguageStaticConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis on 1/14/2017.
 */
public class DLanguageStaticConstructorStub extends StubBase<DLanguageStaticConstructor> implements StubElement<DLanguageStaticConstructor> {
    public DLanguageStaticConstructorStub(final StubElement parent, @NotNull final IStubElementType elementType) {
        super(parent, elementType);
    }


}
