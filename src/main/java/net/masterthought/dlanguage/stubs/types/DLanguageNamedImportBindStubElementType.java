package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageNamedImportBind;
import net.masterthought.dlanguage.psi.impl.named.DLanguageNamedImportBindImpl;
import net.masterthought.dlanguage.stubs.DLanguageNamedImportBindStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageNamedImportBindStubElementType extends DNamedStubElementType<DLanguageNamedImportBindStub, DLanguageNamedImportBind> {
    public DLanguageNamedImportBindStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageNamedImportBind createPsi(@NotNull DLanguageNamedImportBindStub stub) {
        return new DLanguageNamedImportBindImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageNamedImportBindStub createStub(@NotNull DLanguageNamedImportBind psi, StubElement parentStub) {
        return new DLanguageNamedImportBindStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageNamedImportBindStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageNamedImportBindStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageNamedImportBindStub(parentStub, this, dataStream.readName());
    }
}
