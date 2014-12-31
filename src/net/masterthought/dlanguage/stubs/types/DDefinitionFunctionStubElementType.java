package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.impl.DDefinitionFunctionImpl;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionFunction;
import net.masterthought.dlanguage.psi.references.DUtil;
import net.masterthought.dlanguage.stubs.DDefinitionFunctionStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DDefinitionFunctionStubElementType extends DNamedStubElementType<DDefinitionFunctionStub, DDefinitionFunction> {
    public DDefinitionFunctionStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DDefinitionFunction createPsi(@NotNull DDefinitionFunctionStub stub) {
        return new DDefinitionFunctionImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DDefinitionFunctionStub createStub(@NotNull DDefinitionFunction psi, StubElement parentStub) {
        return new DDefinitionFunctionStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DDefinitionFunctionStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DDefinitionFunctionStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DDefinitionFunctionStub(parentStub, this, dataStream.readName());
    }
}
