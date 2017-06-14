package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageAliasDeclarationSingle;
import net.masterthought.dlanguage.psi.impl.DLanguageAliasDeclarationSingleImpl;
import net.masterthought.dlanguage.stubs.DLanguageAliasDeclarationSingleStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageAliasDeclarationSingleStubElementType extends DNamedStubElementType<DLanguageAliasDeclarationSingleStub, DLanguageAliasDeclarationSingle> {
    public DLanguageAliasDeclarationSingleStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageAliasDeclarationSingle createPsi(@NotNull DLanguageAliasDeclarationSingleStub stub) {
        return new DLanguageAliasDeclarationSingleImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @NotNull
    @Override
    public DLanguageAliasDeclarationSingleStub createStub(@NotNull DLanguageAliasDeclarationSingle psi, StubElement parentStub) {
        return new DLanguageAliasDeclarationSingleStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageAliasDeclarationSingleStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageAliasDeclarationSingleStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageAliasDeclarationSingleStub(parentStub, this, dataStream.readName());
    }
}
