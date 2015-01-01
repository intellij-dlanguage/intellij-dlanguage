package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.impl.DDefinitionClassImpl;
import net.masterthought.dlanguage.psi.impl.DDefinitionFunctionImpl;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionClass;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionFunction;
import net.masterthought.dlanguage.psi.references.DUtil;
import net.masterthought.dlanguage.stubs.DDefinitionClassStub;
import net.masterthought.dlanguage.stubs.DDefinitionFunctionStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DDefinitionClassStubElementType extends DNamedStubElementType<DDefinitionClassStub, DDefinitionClass> {
    public DDefinitionClassStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DDefinitionClass createPsi(@NotNull DDefinitionClassStub stub) {
        return new DDefinitionClassImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DDefinitionClassStub createStub(@NotNull DDefinitionClass psi, StubElement parentStub) {
        return new DDefinitionClassStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DDefinitionClassStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DDefinitionClassStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DDefinitionClassStub(parentStub, this, dataStream.readName());
    }
}
