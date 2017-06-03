package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageUnionDeclaration;
import net.masterthought.dlanguage.psi.impl.DLanguageUnionDeclarationImpl;
import net.masterthought.dlanguage.stubs.DLanguageUnionDeclarationStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageUnionDeclarationStubElementType extends DNamedStubElementType<DLanguageUnionDeclarationStub, DLanguageUnionDeclaration> {
    public DLanguageUnionDeclarationStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageUnionDeclaration createPsi(@NotNull DLanguageUnionDeclarationStub stub) {
        return new DLanguageUnionDeclarationImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageUnionDeclarationStub createStub(@NotNull DLanguageUnionDeclaration psi, StubElement parentStub) {
        return new DLanguageUnionDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageUnionDeclarationStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageUnionDeclarationStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageUnionDeclarationStub(parentStub, this, dataStream.readName());
    }
}
