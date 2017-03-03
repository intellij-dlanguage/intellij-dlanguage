package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageVarDeclarations;
import net.masterthought.dlanguage.psi.impl.DLanguageVarDeclarationsImpl;
import net.masterthought.dlanguage.stubs.DLanguageVarDeclaratorStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageVarDeclaratorStubElementType extends DNamedStubElementType<DLanguageVarDeclaratorStub, DLanguageVarDeclarations> {
    public DLanguageVarDeclaratorStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageVarDeclarations createPsi(@NotNull DLanguageVarDeclaratorStub stub) {
        return new DLanguageVarDeclarationsImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageVarDeclaratorStub createStub(@NotNull DLanguageVarDeclarations psi, StubElement parentStub) {
        return new DLanguageVarDeclaratorStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageVarDeclaratorStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageVarDeclaratorStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageVarDeclaratorStub(parentStub, this, dataStream.readName());
    }
}
