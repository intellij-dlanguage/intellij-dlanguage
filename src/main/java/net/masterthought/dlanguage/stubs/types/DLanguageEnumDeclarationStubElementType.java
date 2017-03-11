package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageEnumDeclaration;
import net.masterthought.dlanguage.psi.impl.DLanguageEnumDeclarationImpl;
import net.masterthought.dlanguage.stubs.DLanguageEnumDeclarationStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Created by francis on 3/9/2017.
 */
public class DLanguageEnumDeclarationStubElementType extends DNamedStubElementType<DLanguageEnumDeclarationStub, DLanguageEnumDeclaration> {
    public DLanguageEnumDeclarationStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageEnumDeclaration createPsi(@NotNull DLanguageEnumDeclarationStub stub) {
        return new DLanguageEnumDeclarationImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageEnumDeclarationStub createStub(@NotNull DLanguageEnumDeclaration psi, StubElement parentStub) {
        return new DLanguageEnumDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageEnumDeclarationStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageEnumDeclarationStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageEnumDeclarationStub(parentStub, this, dataStream.readName());
    }
}
