package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageFuncDeclaration;
import net.masterthought.dlanguage.psi.impl.DLanguageFuncDeclarationImpl;
import net.masterthought.dlanguage.stubs.DLanguageFuncDeclarationStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageFuncDeclarationStubElementType extends DNamedStubElementType<DLanguageFuncDeclarationStub, DLanguageFuncDeclaration> {
    public DLanguageFuncDeclarationStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageFuncDeclaration createPsi(@NotNull DLanguageFuncDeclarationStub stub) {
        return new DLanguageFuncDeclarationImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageFuncDeclarationStub createStub(@NotNull DLanguageFuncDeclaration psi, StubElement parentStub) {
        return new DLanguageFuncDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageFuncDeclarationStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageFuncDeclarationStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageFuncDeclarationStub(parentStub, this, dataStream.readName());
    }
}
