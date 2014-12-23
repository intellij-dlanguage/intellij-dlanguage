package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DTemplateValueParam;

public class DTemplateValueParamImpl extends DCompositeElementImpl implements DTemplateValueParam{

    public DTemplateValueParamImpl(ASTNode node) {
        super(node);
    }


}
