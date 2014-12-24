package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IStubElementType;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionFunction;
import net.masterthought.dlanguage.stubs.DDefinitionFunctionStub;
import org.jetbrains.annotations.Nullable;

public class DDefinitionFunctionImpl extends DNamedStubbedPsiElementBase<DDefinitionFunctionStub> implements DDefinitionFunction {

    public DDefinitionFunctionImpl(ASTNode node) {
        super(node);
    }

    public DDefinitionFunctionImpl(DDefinitionFunctionStub stub, IStubElementType nodeType) {
        super(stub, nodeType);
    }

    @Nullable
    public PsiElement getNameIdentifier() {
        return DPsiImplUtil.getNameIdentifier(this);
    }

    @Nullable
    public PsiElement setName(String newName) {
        return DPsiImplUtil.setName(this, newName);
    }

}
