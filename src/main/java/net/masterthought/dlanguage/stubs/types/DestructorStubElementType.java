package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import net.masterthought.dlanguage.psi.DLanguageDestructor;
import net.masterthought.dlanguage.psi.impl.DLanguageDestructorImpl;
import net.masterthought.dlanguage.stubs.DlangDestructorStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DestructorStubElementType extends DStubElementType<DlangDestructorStub, DLanguageDestructor> {
    public DestructorStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageDestructor createPsi(@NotNull final DlangDestructorStub stub) {
        return new DLanguageDestructorImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangDestructorStub createStub(@NotNull final DLanguageDestructor psi, final StubElement parentStub) {
        return new DlangDestructorStub(parentStub, this);
    }

    @NotNull
    @Override
    public DlangDestructorStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangDestructorStub(parentStub, this);
    }
}
