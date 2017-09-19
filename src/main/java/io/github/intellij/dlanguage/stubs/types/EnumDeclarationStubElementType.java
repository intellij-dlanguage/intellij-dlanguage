package io.github.intellij.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.stubs.DlangEnumDeclarationStub;
import io.github.intellij.dlanguage.psi.DlangEnumDeclaration;
import io.github.intellij.dlanguage.psi.impl.named.DlangEnumDeclarationImpl;
import io.github.intellij.dlanguage.stubs.DlangEnumDeclarationStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Created by francis on 3/9/2017.
 */
public class EnumDeclarationStubElementType extends DNamedStubElementType<DlangEnumDeclarationStub, DlangEnumDeclaration> {
    public EnumDeclarationStubElementType(final String debugName) {
        super(debugName);
    }

    @Override
    public DlangEnumDeclaration createPsi(@NotNull final DlangEnumDeclarationStub stub) {
        return new DlangEnumDeclarationImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(final ASTNode node) {
        return true;
    }

    @Override
    public DlangEnumDeclarationStub createStub(@NotNull final DlangEnumDeclaration psi, final StubElement parentStub) {
        return new DlangEnumDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull final DlangEnumDeclarationStub stub, @NotNull final StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DlangEnumDeclarationStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new DlangEnumDeclarationStub(parentStub, this, dataStream.readName());
    }
}
