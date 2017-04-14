package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageInterfaceDeclaration;
import net.masterthought.dlanguage.psi.impl.DLanguageInterfaceDeclarationImpl;
import net.masterthought.dlanguage.stubs.DLanguageInterfaceDeclarationStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageInterfaceDeclarationStubElementType extends DNamedStubElementType<DLanguageInterfaceDeclarationStub, DLanguageInterfaceDeclaration> {
    public DLanguageInterfaceDeclarationStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageInterfaceDeclaration createPsi(@NotNull DLanguageInterfaceDeclarationStub stub) {
        return new DLanguageInterfaceDeclarationImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageInterfaceDeclarationStub createStub(@NotNull DLanguageInterfaceDeclaration psi, StubElement parentStub) {
        return new DLanguageInterfaceDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageInterfaceDeclarationStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageInterfaceDeclarationStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageInterfaceDeclarationStub(parentStub, this, dataStream.readName());
    }
}
