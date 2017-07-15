package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageLabeledStatement;
import net.masterthought.dlanguage.psi.impl.DLanguageLabeledStatementImpl;
import net.masterthought.dlanguage.stubs.DLanguageLabeledStatementStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class LabeledStatementStubElementType extends DNamedStubElementType<DLanguageLabeledStatementStub, DLanguageLabeledStatement> {
    public LabeledStatementStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageLabeledStatement createPsi(@NotNull DLanguageLabeledStatementStub stub) {
        return new DLanguageLabeledStatementImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageLabeledStatementStub createStub(@NotNull DLanguageLabeledStatement psi, StubElement parentStub) {
        return new DLanguageLabeledStatementStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageLabeledStatementStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageLabeledStatementStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageLabeledStatementStub(parentStub, this, dataStream.readName());
    }
}
