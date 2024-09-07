package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import io.github.intellij.dlanguage.psi.impl.DLanguageDestructorImpl;
import io.github.intellij.dlanguage.psi.named.DlangDestructor;
import io.github.intellij.dlanguage.stubs.DlangDestructorStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DestructorStubElementType extends
    DStubElementType<DlangDestructorStub, DlangDestructor> {
    public DestructorStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DlangDestructor createPsi(@NotNull final DlangDestructorStub stub) {
        return new DLanguageDestructorImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangDestructorStub createStub(@NotNull final DlangDestructor psi,
        final StubElement parentStub) {
        return new DlangDestructorStub(parentStub, this);
    }

    @NotNull
    @Override
    public DlangDestructorStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangDestructorStub(parentStub, this);
    }
}
