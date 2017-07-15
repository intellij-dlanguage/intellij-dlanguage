package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageFunctionDeclaration;
import net.masterthought.dlanguage.psi.DLanguageFunctionDeclaration;
import net.masterthought.dlanguage.psi.impl.DLanguageFunctionDeclarationImpl;
import net.masterthought.dlanguage.stubs.DLanguageFunctionDeclarationStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class FunctionDeclarationStubElementType extends DNamedStubElementType<DLanguageFunctionDeclarationStub, DLanguageFunctionDeclaration> {
    public FunctionDeclarationStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageFunctionDeclaration createPsi(@NotNull DLanguageFunctionDeclarationStub stub) {
        return new DLanguageFunctionDeclarationImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageFunctionDeclarationStub createStub(@NotNull DLanguageFunctionDeclaration psi, StubElement parentStub) {
        return new DLanguageFunctionDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageFunctionDeclarationStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageFunctionDeclarationStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageFunctionDeclarationStub(parentStub, this, dataStream.readName());
    }
}
