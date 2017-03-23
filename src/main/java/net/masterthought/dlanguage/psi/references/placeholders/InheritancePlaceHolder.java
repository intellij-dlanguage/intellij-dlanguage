package net.masterthought.dlanguage.psi.references.placeholders;

import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.DLanguageIdentifier;
import net.masterthought.dlanguage.psi.interfaces.CanInherit;

import java.util.Map;

/**
 * Created by francis on 3/17/2017.
 */
public class InheritancePlaceHolder implements PlaceHolder {
    private CanInherit canInherit;

    public InheritancePlaceHolder(CanInherit canInherit) {

        this.canInherit = canInherit;
    }

    public CanInherit getCanInherit() {
        return canInherit;
    }

    public Map<String, DLanguageIdentifier> getSuperClassNames() {
        return canInherit.getSuperClassNames();
    }

    @Override
    public PsiElement getElement() {
        return canInherit;
    }
}
