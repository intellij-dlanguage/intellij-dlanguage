package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageClassDeclaration;
import net.masterthought.dlanguage.psi.DLanguageFuncDeclaration;
import net.masterthought.dlanguage.psi.impl.DLanguageClassDeclarationImpl;
import net.masterthought.dlanguage.psi.impl.DLanguageFuncDeclarationImpl;
import net.masterthought.dlanguage.stubs.DLanguageClassDeclarationStub;
import net.masterthought.dlanguage.stubs.DLanguageFuncDeclarationStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageClassDeclarationStubElementType extends DNamedStubElementType<DLanguageClassDeclarationStub, DLanguageClassDeclaration> {
    public DLanguageClassDeclarationStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageClassDeclaration createPsi(@NotNull DLanguageClassDeclarationStub stub) {
        return new DLanguageClassDeclarationImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageClassDeclarationStub createStub(@NotNull DLanguageClassDeclaration psi, StubElement parentStub) {
        return new DLanguageClassDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageClassDeclarationStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageClassDeclarationStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageClassDeclarationStub(parentStub, this, dataStream.readName());
    }
}

