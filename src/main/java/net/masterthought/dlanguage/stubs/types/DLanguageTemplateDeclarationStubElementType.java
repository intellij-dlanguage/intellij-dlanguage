package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageTemplateDeclaration;
import net.masterthought.dlanguage.psi.impl.named.DLanguageTemplateDeclarationImpl;
import net.masterthought.dlanguage.stubs.DLanguageTemplateDeclarationStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageTemplateDeclarationStubElementType extends DNamedStubElementType<DLanguageTemplateDeclarationStub, DLanguageTemplateDeclaration> {
    public DLanguageTemplateDeclarationStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageTemplateDeclaration createPsi(@NotNull final DLanguageTemplateDeclarationStub stub) {
        return new DLanguageTemplateDeclarationImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageTemplateDeclarationStub createStub(@NotNull final DLanguageTemplateDeclaration psi, final StubElement parentStub) {
        return new DLanguageTemplateDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DLanguageTemplateDeclarationStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageTemplateDeclarationStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DLanguageTemplateDeclarationStub(parentStub, this, dataStream.readName());
    }
}
