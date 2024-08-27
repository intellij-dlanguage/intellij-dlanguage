package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import io.github.intellij.dlanguage.psi.DLanguageStaticDestructor;
import io.github.intellij.dlanguage.psi.impl.DLanguageStaticDestructorImpl;
import io.github.intellij.dlanguage.stubs.DlangStaticDestructorStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class StaticDestructorStubElementType extends DStubElementType<DlangStaticDestructorStub, DLanguageStaticDestructor> {
    public StaticDestructorStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageStaticDestructor createPsi(@NotNull final DlangStaticDestructorStub stub) {
        return new DLanguageStaticDestructorImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangStaticDestructorStub createStub(@NotNull final DLanguageStaticDestructor psi, final StubElement parentStub) {
        return new DlangStaticDestructorStub(parentStub, this);
    }

    @NotNull
    @Override
    public DlangStaticDestructorStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangStaticDestructorStub(parentStub, this);
    }
}
