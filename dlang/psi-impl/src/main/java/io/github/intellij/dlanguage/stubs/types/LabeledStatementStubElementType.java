package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.DLanguageLabeledStatement;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageLabeledStatementImpl;
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributes;
import io.github.intellij.dlanguage.stubs.DlangLabeledStatementStub;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;

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
        return new DlangLabeledStatementStub(parentStub, this, psi.getName(), psi.getAttributes());
    }

    @Override
    public void serialize(@NotNull final DlangLabeledStatementStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        stub.getAttributes().write(dataStream);
    }

    @NotNull
    @Override
    public DlangLabeledStatementStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangLabeledStatementStub(parentStub, this, dataStream.readName(),
            DAttributes.Companion.read(dataStream));
    }
}
