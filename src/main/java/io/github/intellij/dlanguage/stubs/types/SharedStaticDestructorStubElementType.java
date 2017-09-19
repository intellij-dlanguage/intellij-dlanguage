package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import io.github.intellij.dlanguage.psi.DLanguageSharedStaticDestructor;
import io.github.intellij.dlanguage.psi.impl.DLanguageSharedStaticDestructorImpl;
import io.github.intellij.dlanguage.stubs.DlangSharedStaticDestructorStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SharedStaticDestructorStubElementType extends DStubElementType<DlangSharedStaticDestructorStub, DLanguageSharedStaticDestructor> {
    public SharedStaticDestructorStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageSharedStaticDestructor createPsi(@NotNull final DlangSharedStaticDestructorStub stub) {
        return new DLanguageSharedStaticDestructorImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangSharedStaticDestructorStub createStub(@NotNull final DLanguageSharedStaticDestructor psi, final StubElement parentStub) {
        return new DlangSharedStaticDestructorStub(parentStub, this);
    }

    @NotNull
    @Override
    public DlangSharedStaticDestructorStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangSharedStaticDestructorStub(parentStub, this);
    }
}
