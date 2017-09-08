package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageAutoDeclarationPart;
import net.masterthought.dlanguage.psi.impl.named.DLanguageAutoDeclarationPartImpl;
import net.masterthought.dlanguage.stubs.DLanguageAutoDeclarationPartStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class AutoDeclarationPartStubElementType extends DNamedStubElementType<DLanguageAutoDeclarationPartStub, DLanguageAutoDeclarationPart> {
    public AutoDeclarationPartStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageAutoDeclarationPart createPsi(@NotNull final DLanguageAutoDeclarationPartStub stub) {
        return new DLanguageAutoDeclarationPartImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageAutoDeclarationPartStub createStub(@NotNull final DLanguageAutoDeclarationPart psi, final StubElement parentStub) {
        return new DLanguageAutoDeclarationPartStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DLanguageAutoDeclarationPartStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageAutoDeclarationPartStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DLanguageAutoDeclarationPartStub(parentStub, this, dataStream.readName());
    }
}
