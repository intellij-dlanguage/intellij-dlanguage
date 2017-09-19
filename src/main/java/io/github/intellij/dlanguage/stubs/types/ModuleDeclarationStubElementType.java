package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.DLanguageModuleDeclaration;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageModuleDeclarationImpl;
import io.github.intellij.dlanguage.stubs.DlangModuleDeclarationStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ModuleDeclarationStubElementType extends DNamedStubElementType<DlangModuleDeclarationStub, DLanguageModuleDeclaration> {
    public ModuleDeclarationStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageModuleDeclaration createPsi(@NotNull final DlangModuleDeclarationStub stub) {
        return new DLanguageModuleDeclarationImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangModuleDeclarationStub createStub(@NotNull final DLanguageModuleDeclaration psi, final StubElement parentStub) {
        return new DlangModuleDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DlangModuleDeclarationStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DlangModuleDeclarationStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangModuleDeclarationStub(parentStub, this, dataStream.readName());
    }
}
