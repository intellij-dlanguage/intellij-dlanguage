package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DTemplateTypeParam;

public class DTemplateTypeParamImpl extends DCompositeElementImpl implements DTemplateTypeParam{

    public DTemplateTypeParamImpl(ASTNode node) {
        super(node);
    }


}
