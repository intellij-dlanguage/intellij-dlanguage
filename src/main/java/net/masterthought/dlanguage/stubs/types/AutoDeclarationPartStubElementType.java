package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageAutoDeclarationPart;
import net.masterthought.dlanguage.psi.impl.DLanguageAutoDeclarationImpl;
import net.masterthought.dlanguage.psi.impl.DLanguageAutoDeclarationPartImpl;
import net.masterthought.dlanguage.stubs.DLanguageAutoDeclarationPartStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class AutoDeclarationPartStubElementType extends DNamedStubElementType<DLanguageAutoDeclarationPartStub, DLanguageAutoDeclarationPart> {
    public AutoDeclarationPartStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageAutoDeclarationPart createPsi(@NotNull DLanguageAutoDeclarationPartStub stub) {
        return new DLanguageAutoDeclarationPartImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return true;
    }

    @Override
    public DLanguageAutoDeclarationPartStub createStub(@NotNull DLanguageAutoDeclarationPart psi, StubElement parentStub) {
        return new DLanguageAutoDeclarationPartStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageAutoDeclarationPartStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageAutoDeclarationPartStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageAutoDeclarationPartStub(parentStub, this, dataStream.readName());
    }
}
