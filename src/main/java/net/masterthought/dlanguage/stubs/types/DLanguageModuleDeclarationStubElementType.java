package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageGlobalDeclaration;
import net.masterthought.dlanguage.psi.impl.DLanguageGlobalDeclarationImpl;
import net.masterthought.dlanguage.stubs.DLanguageModuleDeclarationStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageModuleDeclarationStubElementType extends DNamedStubElementType<DLanguageModuleDeclarationStub, DLanguageGlobalDeclaration> {
    public DLanguageModuleDeclarationStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageGlobalDeclaration createPsi(@NotNull DLanguageModuleDeclarationStub stub) {
        return new DLanguageGlobalDeclarationImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageModuleDeclarationStub createStub(@NotNull DLanguageGlobalDeclaration psi, StubElement parentStub) {
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
