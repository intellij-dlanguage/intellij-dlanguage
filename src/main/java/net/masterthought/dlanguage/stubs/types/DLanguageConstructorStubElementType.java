package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageConstructor;
import net.masterthought.dlanguage.psi.impl.DLanguageConstructorImpl;
import net.masterthought.dlanguage.stubs.DLanguageConstructorStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageConstructorStubElementType extends DNamedStubElementType<DLanguageConstructorStub, DLanguageConstructor> {
    public DLanguageConstructorStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageConstructor createPsi(@NotNull DLanguageConstructorStub stub) {
        return new DLanguageConstructorImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageConstructorStub createStub(@NotNull DLanguageConstructor psi, StubElement parentStub) {
        return new DLanguageConstructorStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageConstructorStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageConstructorStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageConstructorStub(parentStub, this, dataStream.readName());
    }
}
