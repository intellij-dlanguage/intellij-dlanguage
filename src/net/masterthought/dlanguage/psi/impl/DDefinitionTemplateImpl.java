package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import net.masterthought.dlanguage.psi.interfaces.DDefinitionTemplate;

public class DDefinitionTemplateImpl extends DCompositeElementImpl implements DDefinitionTemplate{

    public DDefinitionTemplateImpl(ASTNode node) {
        super(node);
    }


}
