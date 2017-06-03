package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageStaticDestructor;
import net.masterthought.dlanguage.psi.impl.DLanguageStaticDestructorImpl;
import net.masterthought.dlanguage.stubs.DLanguageStaticDestructorStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageStaticDestructorStubElementType extends DStubElementType<DLanguageStaticDestructorStub, DLanguageStaticDestructor> {
    public DLanguageStaticDestructorStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageStaticDestructor createPsi(@NotNull DLanguageStaticDestructorStub stub) {
        return new DLanguageStaticDestructorImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageStaticDestructorStub createStub(@NotNull DLanguageStaticDestructor psi, StubElement parentStub) {
        return new DLanguageStaticDestructorStub(parentStub, this);
    }

    @Override
    public void serialize(@NotNull DLanguageStaticDestructorStub stub, @NotNull StubOutputStream dataStream) throws IOException {
    }

    @NotNull
    @Override
    public DLanguageStaticDestructorStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageStaticDestructorStub(parentStub, this);
    }

    @Override
    public void indexStub(@NotNull DLanguageStaticDestructorStub stub, @NotNull IndexSink sink) {

    }
}
