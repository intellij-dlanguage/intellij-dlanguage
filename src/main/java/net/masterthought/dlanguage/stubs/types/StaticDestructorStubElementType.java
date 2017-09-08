package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import net.masterthought.dlanguage.psi.DLanguageStaticDestructor;
import net.masterthought.dlanguage.psi.impl.DLanguageStaticDestructorImpl;
import net.masterthought.dlanguage.stubs.DLanguageStaticDestructorStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class StaticDestructorStubElementType extends DStubElementType<DLanguageStaticDestructorStub, DLanguageStaticDestructor> {
    public StaticDestructorStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageStaticDestructor createPsi(@NotNull final DLanguageStaticDestructorStub stub) {
        return new DLanguageStaticDestructorImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageStaticDestructorStub createStub(@NotNull final DLanguageStaticDestructor psi, final StubElement parentStub) {
        return new DLanguageStaticDestructorStub(parentStub, this);
    }

    @NotNull
    @Override
    public DLanguageStaticDestructorStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DLanguageStaticDestructorStub(parentStub, this);
    }
}
