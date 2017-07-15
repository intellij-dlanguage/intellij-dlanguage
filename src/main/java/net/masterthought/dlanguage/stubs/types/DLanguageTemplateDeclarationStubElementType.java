package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageTemplateDeclaration;
import net.masterthought.dlanguage.psi.impl.DLanguageTemplateDeclarationImpl;
import net.masterthought.dlanguage.stubs.DLanguageTemplateDeclarationStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageTemplateDeclarationStubElementType extends DNamedStubElementType<DLanguageTemplateDeclarationStub, DLanguageTemplateDeclaration> {
    public DLanguageTemplateDeclarationStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageTemplateDeclaration createPsi(@NotNull DLanguageTemplateDeclarationStub stub) {
        return new DLanguageTemplateDeclarationImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @NotNull
    @Override
    public DLanguageTemplateDeclarationStub createStub(@NotNull DLanguageTemplateDeclaration psi, StubElement parentStub) {
        return new DLanguageTemplateDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageTemplateDeclarationStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageTemplateDeclarationStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageTemplateDeclarationStub(parentStub, this, dataStream.readName());
    }
}
