package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageModuleDeclaration;
import net.masterthought.dlanguage.stubs.DLanguageModuleDeclarationStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageModuleDeclarationStubElementType extends DNamedStubElementType<DLanguageModuleDeclarationStub, DLanguageModuleDeclaration> {
    public DLanguageModuleDeclarationStubElementType(String debugName) {
        super(debugName);
    }



    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageModuleDeclaration createPsi(@NotNull DLanguageModuleDeclarationStub stub) {
        return null;
    }

    @Override
    public DLanguageModuleDeclarationStub createStub(@NotNull DLanguageModuleDeclaration psi, StubElement parentStub) {
        return new DLanguageModuleDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageModuleDeclarationStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageModuleDeclarationStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageModuleDeclarationStub(parentStub, this, dataStream.readName());
    }
}
