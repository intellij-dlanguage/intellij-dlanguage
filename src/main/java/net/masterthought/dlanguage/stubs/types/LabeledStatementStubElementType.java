package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageLabeledStatement;
import net.masterthought.dlanguage.psi.impl.named.DLanguageLabeledStatementImpl;
import net.masterthought.dlanguage.stubs.DLanguageLabeledStatementStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class LabeledStatementStubElementType extends DNamedStubElementType<DLanguageLabeledStatementStub, DLanguageLabeledStatement> {
    public LabeledStatementStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageLabeledStatement createPsi(@NotNull final DLanguageLabeledStatementStub stub) {
        return new DLanguageLabeledStatementImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageLabeledStatementStub createStub(@NotNull final DLanguageLabeledStatement psi, final StubElement parentStub) {
        return new DLanguageLabeledStatementStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DLanguageLabeledStatementStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageLabeledStatementStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DLanguageLabeledStatementStub(parentStub, this, dataStream.readName());
    }
}
