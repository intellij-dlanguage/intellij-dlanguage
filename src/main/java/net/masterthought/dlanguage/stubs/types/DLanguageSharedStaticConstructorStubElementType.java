package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageSharedStaticConstructor;
import net.masterthought.dlanguage.psi.impl.DLanguageSharedStaticConstructorImpl;
import net.masterthought.dlanguage.stubs.DLanguageSharedStaticConstructorStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageSharedStaticConstructorStubElementType extends DStubElementType<DLanguageSharedStaticConstructorStub, DLanguageSharedStaticConstructor> {
    public DLanguageSharedStaticConstructorStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageSharedStaticConstructor createPsi(@NotNull DLanguageSharedStaticConstructorStub stub) {
        return new DLanguageSharedStaticConstructorImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageSharedStaticConstructorStub createStub(@NotNull DLanguageSharedStaticConstructor psi, StubElement parentStub) {
        return new DLanguageSharedStaticConstructorStub(parentStub, this);
    }

    @Override
    public void serialize(@NotNull DLanguageSharedStaticConstructorStub stub, @NotNull StubOutputStream dataStream) throws IOException {

    }

    @NotNull
    @Override
    public DLanguageSharedStaticConstructorStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageSharedStaticConstructorStub(parentStub, this);
    }

    @Override
    public void indexStub(@NotNull DLanguageSharedStaticConstructorStub stub, @NotNull IndexSink sink) {
        //todo at some point add static constructos to index under module name
    }
}
