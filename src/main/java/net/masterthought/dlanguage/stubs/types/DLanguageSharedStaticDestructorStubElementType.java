package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageSharedStaticDestructor;
import net.masterthought.dlanguage.psi.impl.DLanguageSharedStaticDestructorImpl;
import net.masterthought.dlanguage.stubs.DLanguageSharedStaticDestructorStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageSharedStaticDestructorStubElementType extends DNamedStubElementType<DLanguageSharedStaticDestructorStub, DLanguageSharedStaticDestructor> {
    public DLanguageSharedStaticDestructorStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageSharedStaticDestructor createPsi(@NotNull DLanguageSharedStaticDestructorStub stub) {
        return new DLanguageSharedStaticDestructorImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageSharedStaticDestructorStub createStub(@NotNull DLanguageSharedStaticDestructor psi, StubElement parentStub) {
        return new DLanguageSharedStaticDestructorStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageSharedStaticDestructorStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageSharedStaticDestructorStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageSharedStaticDestructorStub(parentStub, this, dataStream.readName());
    }
}
