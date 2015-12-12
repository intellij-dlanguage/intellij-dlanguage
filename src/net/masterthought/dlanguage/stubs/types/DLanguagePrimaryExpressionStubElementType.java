package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguagePrimaryExpression;
import net.masterthought.dlanguage.psi.impl.DLanguagePrimaryExpressionImpl;
import net.masterthought.dlanguage.stubs.DLanguagePrimaryExpressionStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguagePrimaryExpressionStubElementType extends DNamedStubElementType<DLanguagePrimaryExpressionStub, DLanguagePrimaryExpression> {
    public DLanguagePrimaryExpressionStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguagePrimaryExpression createPsi(@NotNull DLanguagePrimaryExpressionStub stub) {
        return new DLanguagePrimaryExpressionImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguagePrimaryExpressionStub createStub(@NotNull DLanguagePrimaryExpression psi, StubElement parentStub) {
        return new DLanguagePrimaryExpressionStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguagePrimaryExpressionStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguagePrimaryExpressionStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguagePrimaryExpressionStub(parentStub, this, dataStream.readName());
    }
}

