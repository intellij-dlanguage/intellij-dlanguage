package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageDestructor;
import net.masterthought.dlanguage.psi.impl.DLanguageDestructorImpl;
import net.masterthought.dlanguage.stubs.DLanguageDestructorStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageDestructorStubElementType extends DNamedStubElementType<DLanguageDestructorStub, DLanguageDestructor> {
    public DLanguageDestructorStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageDestructor createPsi(@NotNull DLanguageDestructorStub stub) {
        return new DLanguageDestructorImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageDestructorStub createStub(@NotNull DLanguageDestructor psi, StubElement parentStub) {
        return new DLanguageDestructorStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageDestructorStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageDestructorStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageDestructorStub(parentStub, this, dataStream.readName());
    }
}
