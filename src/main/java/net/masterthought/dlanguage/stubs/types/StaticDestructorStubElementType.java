package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import net.masterthought.dlanguage.psi.DLanguageStaticDestructor;
import net.masterthought.dlanguage.psi.impl.DLanguageStaticDestructorImpl;
import net.masterthought.dlanguage.stubs.DlangStaticDestructorStub;
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
