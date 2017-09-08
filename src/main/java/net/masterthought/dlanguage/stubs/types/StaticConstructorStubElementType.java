package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import net.masterthought.dlanguage.psi.DLanguageStaticConstructor;
import net.masterthought.dlanguage.psi.impl.DLanguageStaticConstructorImpl;
import net.masterthought.dlanguage.stubs.DLanguageStaticConstructorStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class StaticConstructorStubElementType extends DStubElementType<DLanguageStaticConstructorStub, DLanguageStaticConstructor> {
    public StaticConstructorStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageStaticConstructor createPsi(@NotNull final DLanguageStaticConstructorStub stub) {
        return new DLanguageStaticConstructorImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageStaticConstructorStub createStub(@NotNull final DLanguageStaticConstructor psi, final StubElement parentStub) {
        return new DLanguageStaticConstructorStub(parentStub, this);
    }

    @NotNull
    @Override
    public DLanguageStaticConstructorStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DLanguageStaticConstructorStub(parentStub, this);
    }
}
