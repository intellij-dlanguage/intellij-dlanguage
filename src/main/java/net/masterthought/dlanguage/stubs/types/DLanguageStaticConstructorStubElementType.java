package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageStaticConstructor;
import net.masterthought.dlanguage.psi.impl.DLanguageStaticConstructorImpl;
import net.masterthought.dlanguage.stubs.DLanguageStaticConstructorStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageStaticConstructorStubElementType extends DNamedStubElementType<DLanguageStaticConstructorStub, DLanguageStaticConstructor> {
    public DLanguageStaticConstructorStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageStaticConstructor createPsi(@NotNull DLanguageStaticConstructorStub stub) {
        return new DLanguageStaticConstructorImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageStaticConstructorStub createStub(@NotNull DLanguageStaticConstructor psi, StubElement parentStub) {
        return new DLanguageStaticConstructorStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageStaticConstructorStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageStaticConstructorStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageStaticConstructorStub(parentStub, this, dataStream.readName());
    }
}
