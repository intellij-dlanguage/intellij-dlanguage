package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.named.DlangDeclaratorIdentifier;
import io.github.intellij.dlanguage.psi.impl.named.DlangDeclaratorIdentifierImpl;
import io.github.intellij.dlanguage.stubs.DlangDeclaratorIdentifierStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DeclaratorIdentifierStubElementType extends
    DNamedStubElementType<DlangDeclaratorIdentifierStub, DlangDeclaratorIdentifier> {
    public DeclaratorIdentifierStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DlangDeclaratorIdentifier createPsi(@NotNull final DlangDeclaratorIdentifierStub stub) {
        return new DlangDeclaratorIdentifierImpl(stub, this);
    }

    @Override
    public @NotNull DlangDeclaratorIdentifierStub createStub(@NotNull DlangDeclaratorIdentifier psi, StubElement<? extends PsiElement> parentStub) {
        return new DlangDeclaratorIdentifierStub(parentStub, this, psi.getName(), null);
    }

    @Override
    public void serialize(@NotNull final DlangDeclaratorIdentifierStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }
    @Override
    public @NotNull DlangDeclaratorIdentifierStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DlangDeclaratorIdentifierStub(parentStub, this, dataStream.readName(), null);
    }
}
