package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.DLanguageAutoDeclarationPart;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageAutoDeclarationPartImpl;
import io.github.intellij.dlanguage.stubs.DlangAutoDeclarationPartStub;
import io.github.intellij.dlanguage.psi.DLanguageAutoDeclarationPart;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageAutoDeclarationPartImpl;
import io.github.intellij.dlanguage.stubs.DlangAutoDeclarationPartStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class AutoDeclarationPartStubElementType extends DNamedStubElementType<DlangAutoDeclarationPartStub, DLanguageAutoDeclarationPart> {
    public AutoDeclarationPartStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageAutoDeclarationPart createPsi(@NotNull final DlangAutoDeclarationPartStub stub) {
        return new DLanguageAutoDeclarationPartImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangAutoDeclarationPartStub createStub(@NotNull final DLanguageAutoDeclarationPart psi, final StubElement parentStub) {
        return new DlangAutoDeclarationPartStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DlangAutoDeclarationPartStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DlangAutoDeclarationPartStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangAutoDeclarationPartStub(parentStub, this, dataStream.readName());
    }
}
