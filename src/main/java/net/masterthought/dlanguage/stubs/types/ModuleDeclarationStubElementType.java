package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageModuleDeclaration;
import net.masterthought.dlanguage.psi.impl.named.DLanguageModuleDeclarationImpl;
import net.masterthought.dlanguage.stubs.DLanguageModuleDeclarationStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ModuleDeclarationStubElementType extends DNamedStubElementType<DLanguageModuleDeclarationStub, DLanguageModuleDeclaration> {
    public ModuleDeclarationStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageModuleDeclaration createPsi(@NotNull final DLanguageModuleDeclarationStub stub) {
        return new DLanguageModuleDeclarationImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageModuleDeclarationStub createStub(@NotNull final DLanguageModuleDeclaration psi, final StubElement parentStub) {
        return new DLanguageModuleDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DLanguageModuleDeclarationStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageModuleDeclarationStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DLanguageModuleDeclarationStub(parentStub, this, dataStream.readName());
    }
}
