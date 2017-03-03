package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageAutoDeclarationY;
import net.masterthought.dlanguage.psi.impl.DLanguageAutoDeclarationYImpl;
import net.masterthought.dlanguage.stubs.DLanguageAutoDeclarationStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageAutoDeclarationStubElementType extends DNamedStubElementType<DLanguageAutoDeclarationStub, DLanguageAutoDeclarationY> {
    public DLanguageAutoDeclarationStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageAutoDeclarationY createPsi(@NotNull DLanguageAutoDeclarationStub stub) {
        return new DLanguageAutoDeclarationYImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageAutoDeclarationStub createStub(@NotNull DLanguageAutoDeclarationY psi, StubElement parentStub) {
        return new DLanguageAutoDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageAutoDeclarationStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageAutoDeclarationStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageAutoDeclarationStub(parentStub, this, dataStream.readName());
    }
}
