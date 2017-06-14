package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import net.masterthought.dlanguage.psi.DLanguageSharedStaticDestructor;
import net.masterthought.dlanguage.psi.impl.DLanguageSharedStaticDestructorImpl;
import net.masterthought.dlanguage.stubs.DLanguageSharedStaticDestructorStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageSharedStaticDestructorStubElementType extends DStubElementType<DLanguageSharedStaticDestructorStub, DLanguageSharedStaticDestructor> {
    public DLanguageSharedStaticDestructorStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageSharedStaticDestructor createPsi(@NotNull DLanguageSharedStaticDestructorStub stub) {
        return new DLanguageSharedStaticDestructorImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageSharedStaticDestructorStub createStub(@NotNull DLanguageSharedStaticDestructor psi, StubElement parentStub) {
        return new DLanguageSharedStaticDestructorStub(parentStub, this);
    }

    @NotNull
    @Override
    public DLanguageSharedStaticDestructorStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageSharedStaticDestructorStub(parentStub, this);
    }
}
