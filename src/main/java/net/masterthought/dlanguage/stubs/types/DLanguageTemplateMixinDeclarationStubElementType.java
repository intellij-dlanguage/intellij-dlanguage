package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.psi.DLanguageTemplateMixinDeclaration;
import net.masterthought.dlanguage.psi.impl.DLanguageTemplateMixinDeclarationImpl;
import net.masterthought.dlanguage.stubs.DLanguageTemplateMixinDeclarationStub;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DLanguageTemplateMixinDeclarationStubElementType extends DNamedStubElementType<DLanguageTemplateMixinDeclarationStub, DLanguageTemplateMixinDeclaration> {
    public DLanguageTemplateMixinDeclarationStubElementType(String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageTemplateMixinDeclaration createPsi(@NotNull DLanguageTemplateMixinDeclarationStub stub) {
        return new DLanguageTemplateMixinDeclarationImpl(stub, this);
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return DUtil.definitionNode(node);
    }

    @Override
    public DLanguageTemplateMixinDeclarationStub createStub(@NotNull DLanguageTemplateMixinDeclaration psi, StubElement parentStub) {
        return new DLanguageTemplateMixinDeclarationStub(parentStub, this, psi.getName());
    }

    @Override
    public void serialize(@NotNull DLanguageTemplateMixinDeclarationStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public DLanguageTemplateMixinDeclarationStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new DLanguageTemplateMixinDeclarationStub(parentStub, this, dataStream.readName());
    }
}
