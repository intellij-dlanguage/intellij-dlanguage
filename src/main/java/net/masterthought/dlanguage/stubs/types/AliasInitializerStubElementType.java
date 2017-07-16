package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageAliasInitializer;
import net.masterthought.dlanguage.psi.impl.named.DLanguageAliasInitializerImpl;
import net.masterthought.dlanguage.stubs.DLanguageAliasInitializerStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class AliasInitializerStubElementType extends DNamedStubElementType<DLanguageAliasInitializerStub, DLanguageAliasInitializer> {
    public AliasInitializerStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageAliasInitializer createPsi(@NotNull DLanguageAliasInitializerStub stub) {
        return new DLanguageAliasInitializerImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return true;
    }

    @NotNull
    @Override
    public DLanguageAliasInitializerStub createStub(@NotNull DLanguageAliasInitializer psi, StubElement parentStub) {
        return new DLanguageAliasInitializerStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageAliasInitializerStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageAliasInitializerStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageAliasInitializerStub(parentStub, this, dataStream.readName());
    }
}
