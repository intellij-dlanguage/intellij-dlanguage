package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import net.masterthought.dlanguage.psi.DLanguageDestructor;
import net.masterthought.dlanguage.psi.impl.DLanguageDestructorImpl;
import net.masterthought.dlanguage.stubs.DLanguageDestructorStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DestructorStubElementType extends DStubElementType<DLanguageDestructorStub, DLanguageDestructor> {
    public DestructorStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageDestructor createPsi(@NotNull DLanguageDestructorStub stub) {
        return new DLanguageDestructorImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageDestructorStub createStub(@NotNull DLanguageDestructor psi, StubElement parentStub) {
        return new DLanguageDestructorStub(parentStub, this);
    }

    @NotNull
    @Override
    public DLanguageDestructorStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageDestructorStub(parentStub, this);
    }
}
