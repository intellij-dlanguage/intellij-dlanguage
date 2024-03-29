package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import io.github.intellij.dlanguage.psi.impl.DLanguageSharedStaticConstructorImpl;
import io.github.intellij.dlanguage.stubs.DlangSharedStaticConstructorStub;
import io.github.intellij.dlanguage.psi.DLanguageSharedStaticConstructor;
import io.github.intellij.dlanguage.psi.impl.DLanguageSharedStaticConstructorImpl;
import io.github.intellij.dlanguage.stubs.DlangSharedStaticConstructorStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SharedStaticConstructorStubElementType extends DStubElementType<DlangSharedStaticConstructorStub, DLanguageSharedStaticConstructor> {
    public SharedStaticConstructorStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageSharedStaticConstructor createPsi(@NotNull final DlangSharedStaticConstructorStub stub) {
        return new DLanguageSharedStaticConstructorImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangSharedStaticConstructorStub createStub(@NotNull final DLanguageSharedStaticConstructor psi, final StubElement parentStub) {
        return new DlangSharedStaticConstructorStub(parentStub, this);
    }

    @NotNull
    @Override
    public DlangSharedStaticConstructorStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangSharedStaticConstructorStub(parentStub, this);
    }
}
