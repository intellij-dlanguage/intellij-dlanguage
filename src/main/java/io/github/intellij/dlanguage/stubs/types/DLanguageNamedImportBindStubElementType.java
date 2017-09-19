package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.psi.DLanguageNamedImportBind;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageNamedImportBindImpl;
import io.github.intellij.dlanguage.stubs.DlangNamedImportBindStub;
import io.github.intellij.dlanguage.psi.DLanguageNamedImportBind;
import io.github.intellij.dlanguage.psi.impl.named.DLanguageNamedImportBindImpl;
import io.github.intellij.dlanguage.stubs.DlangNamedImportBindStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageNamedImportBindStubElementType extends DNamedStubElementType<DlangNamedImportBindStub, DLanguageNamedImportBind> {
    public DLanguageNamedImportBindStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageNamedImportBind createPsi(@NotNull final DlangNamedImportBindStub stub) {
        return new DLanguageNamedImportBindImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangNamedImportBindStub createStub(@NotNull final DLanguageNamedImportBind psi, final StubElement parentStub) {
        return new DlangNamedImportBindStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DlangNamedImportBindStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DlangNamedImportBindStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangNamedImportBindStub(parentStub, this, dataStream.readName());
    }
}
