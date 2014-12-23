package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DTemplateThisParam;

public class DTemplateThisParamImpl extends DCompositeElementImpl implements DTemplateThisParam{

    public DTemplateThisParamImpl(ASTNode node) {
        super(node);
    }


}
