package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import net.masterthought.dlanguage.psi.DLanguageSharedStaticConstructor;
import net.masterthought.dlanguage.psi.impl.DLanguageSharedStaticConstructorImpl;
import net.masterthought.dlanguage.stubs.DLanguageSharedStaticConstructorStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SharedStaticConstructorStubElementType extends DStubElementType<DLanguageSharedStaticConstructorStub, DLanguageSharedStaticConstructor> {
    public SharedStaticConstructorStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageSharedStaticConstructor createPsi(@NotNull DLanguageSharedStaticConstructorStub stub) {
        return new DLanguageSharedStaticConstructorImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageSharedStaticConstructorStub createStub(@NotNull DLanguageSharedStaticConstructor psi, StubElement parentStub) {
        return new DLanguageSharedStaticConstructorStub(parentStub, this);
    }

    @NotNull
    @Override
    public DLanguageSharedStaticConstructorStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageSharedStaticConstructorStub(parentStub, this);
    }
}
