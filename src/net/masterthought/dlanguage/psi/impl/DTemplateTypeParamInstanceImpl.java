package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DTemplateTypeParamInstance;

public class DTemplateTypeParamInstanceImpl extends DCompositeElementImpl implements DTemplateTypeParamInstance{

    public DTemplateTypeParamInstanceImpl(ASTNode node) {
        super(node);
    }


}
