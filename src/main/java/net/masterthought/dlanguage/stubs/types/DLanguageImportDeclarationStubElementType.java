package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageImport;
import net.masterthought.dlanguage.psi.impl.DLanguageImportImpl;
import net.masterthought.dlanguage.stubs.DLanguageImportDeclarationStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Created by francis on 3/15/2017.
 */
public class DLanguageImportDeclarationStubElementType extends DNamedStubElementType<DLanguageImportDeclarationStub, DLanguageImport> {
    public DLanguageImportDeclarationStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageImport createPsi(@NotNull DLanguageImportDeclarationStub stub) {
        return new DLanguageImportImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @NotNull
    @Override
    public DLanguageImportDeclarationStub createStub(@NotNull DLanguageImport psi, StubElement parentStub) {
        return new DLanguageImportDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageImportDeclarationStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageImportDeclarationStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageImportDeclarationStub(parentStub, this, dataStream.readName());
    }
}
