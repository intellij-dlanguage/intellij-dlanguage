package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageDeclaratorInitializer;
import net.masterthought.dlanguage.psi.impl.DLanguageDeclaratorInitializerImpl;
import net.masterthought.dlanguage.stubs.DLanguageDeclaratorInitializerStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageDeclaratorInitializerStubElementType extends DNamedStubElementType<DLanguageDeclaratorInitializerStub, DLanguageDeclaratorInitializer> {
    public DLanguageDeclaratorInitializerStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageDeclaratorInitializer createPsi(@NotNull DLanguageDeclaratorInitializerStub stub) {
        return new DLanguageDeclaratorInitializerImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageDeclaratorInitializerStub createStub(@NotNull DLanguageDeclaratorInitializer psi, StubElement parentStub) {
        return new DLanguageDeclaratorInitializerStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageDeclaratorInitializerStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageDeclaratorInitializerStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageDeclaratorInitializerStub(parentStub, this, dataStream.readName());
    }
}
