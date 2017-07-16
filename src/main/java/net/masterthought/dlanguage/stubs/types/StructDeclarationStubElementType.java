package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageStructDeclaration;
import net.masterthought.dlanguage.psi.impl.named.DLanguageStructDeclarationImpl;
import net.masterthought.dlanguage.stubs.DLanguageStructDeclarationStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class StructDeclarationStubElementType extends DNamedStubElementType<DLanguageStructDeclarationStub, DLanguageStructDeclaration> {
    public StructDeclarationStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageStructDeclaration createPsi(@NotNull DLanguageStructDeclarationStub stub) {
        return new DLanguageStructDeclarationImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return true;
    }

    @NotNull
    @Override
    public DLanguageStructDeclarationStub createStub(@NotNull DLanguageStructDeclaration psi, StubElement parentStub) {
        return new DLanguageStructDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageStructDeclarationStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageStructDeclarationStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageStructDeclarationStub(parentStub, this, dataStream.readName());
    }
}
