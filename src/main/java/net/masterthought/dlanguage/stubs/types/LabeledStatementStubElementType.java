package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageLabeledStatement;
import net.masterthought.dlanguage.psi.impl.named.DLanguageLabeledStatementImpl;
import net.masterthought.dlanguage.stubs.DlangLabeledStatementStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class LabeledStatementStubElementType extends DNamedStubElementType<DlangLabeledStatementStub, DLanguageLabeledStatement> {
    public LabeledStatementStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageLabeledStatement createPsi(@NotNull final DlangLabeledStatementStub stub) {
        return new DLanguageLabeledStatementImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangLabeledStatementStub createStub(@NotNull final DLanguageLabeledStatement psi, final StubElement parentStub) {
        return new DlangLabeledStatementStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DlangLabeledStatementStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DlangLabeledStatementStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangLabeledStatementStub(parentStub, this, dataStream.readName());
    }
}
