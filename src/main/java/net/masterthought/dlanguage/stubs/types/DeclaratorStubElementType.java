package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageDeclarator;
import net.masterthought.dlanguage.psi.impl.DLanguageDeclaratorImpl;
import net.masterthought.dlanguage.stubs.DLanguageDeclaratorStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DeclaratorStubElementType extends DNamedStubElementType<DLanguageDeclaratorStub, DLanguageDeclarator> {
    public DeclaratorStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageDeclarator createPsi(@NotNull DLanguageDeclaratorStub stub) {
        return new DLanguageDeclaratorImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageDeclaratorStub createStub(@NotNull DLanguageDeclarator psi, StubElement parentStub) {
        return new DLanguageDeclaratorStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageDeclaratorStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageDeclaratorStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageDeclaratorStub(parentStub, this, dataStream.readName());
    }
}
