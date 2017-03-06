package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.utils.DUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by francis on 2/28/2017.
 */
public interface TemplateContainer extends DNamedElement {
    default List<DLanguageTemplateDeclaration> getTemplateDeclarations(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        if (includeFromInheritance) {
            //todo
        }
        return getTemplateDeclarationsImpl(this, includeFromMixins, includeNestedDeclarations);

//        throw new NotImplementedException();
    }

    @NotNull
    default List<DLanguageTemplateDeclaration> getTemplateDeclarationsImpl(PsiElement elementToSearch, boolean includeFromMixins, boolean includeNestedDeclarations) {
        List<DLanguageTemplateDeclaration> res = new ArrayList<>();
        if (elementToSearch instanceof DLanguageTemplateDeclaration) {
            if (!includeNestedDeclarations) {
                return Collections.singletonList((DLanguageTemplateDeclaration) elementToSearch);
            }
            res.add((DLanguageTemplateDeclaration) elementToSearch);
        }

        if (includeFromMixins) {
            if (elementToSearch instanceof DLanguageMixinDeclaration) {
                final DLanguageMixinDeclaration mixin = (DLanguageMixinDeclaration) elementToSearch;
                if (mixin.getTemplateDeclaration() != null) {
                    res.addAll(mixin.getTemplateDeclaration().getTemplateDeclarations(true, false, includeNestedDeclarations));
                }
                if (mixin.getTemplateMixinDeclaration() != null) {
                    res.addAll(mixin.getTemplateMixinDeclaration().getTemplateDeclarations(true, false, includeNestedDeclarations));
                }
            }
            if (elementToSearch instanceof DLanguageTemplateMixin) {
                final DLanguageTemplateMixin mixin = (DLanguageTemplateMixin) elementToSearch;
                if (mixin.getTemplateDeclaration() != null) {
                    res.addAll(mixin.getTemplateDeclaration().getTemplateDeclarations(true, false, includeNestedDeclarations));
                }
                if (mixin.getTemplateMixinDeclaration() != null) {
                    res.addAll(mixin.getTemplateMixinDeclaration().getTemplateDeclarations(true, false, includeNestedDeclarations));
                }
            }
            if (elementToSearch instanceof DLanguageMixinExpression) {
                final DLanguageMixinExpression mixin = (DLanguageMixinExpression) elementToSearch;
                if (mixin.getTemplateDeclaration() != null) {
                    res.addAll(mixin.getTemplateDeclaration().getTemplateDeclarations(true, false, includeNestedDeclarations));
                }
                if (mixin.getTemplateMixinDeclaration() != null) {
                    res.addAll(mixin.getTemplateMixinDeclaration().getTemplateDeclarations(true, false, includeNestedDeclarations));
                }
            }
            if (elementToSearch instanceof DLanguageMixinStatement) {
                final DLanguageMixinStatement mixin = (DLanguageMixinStatement) elementToSearch;
                if (mixin.getTemplateDeclaration() != null) {
                    res.addAll(mixin.getTemplateDeclaration().getTemplateDeclarations(true, false, includeNestedDeclarations));
                }
                if (mixin.getTemplateMixinDeclaration() != null) {
                    res.addAll(mixin.getTemplateMixinDeclaration().getTemplateDeclarations(true, false, includeNestedDeclarations));
                }
            }
        }
        if (includeNestedDeclarations && elementToSearch instanceof TemplateContainer && elementToSearch != this) {
            return res;
        }

        res.addAll(doRecursiveCall(elementToSearch, includeFromMixins, includeNestedDeclarations));
        return res;
    }

    @NotNull
    default List<DLanguageTemplateDeclaration> doRecursiveCall(PsiElement elementToSearch, boolean includeFromMixins, boolean includeNestedDeclarations) {
        List<DLanguageTemplateDeclaration> res = new ArrayList<>();
        for (PsiElement child : elementToSearch.getChildren()) {
            res.addAll(getTemplateDeclarationsImpl(child, includeFromMixins, includeNestedDeclarations));
        }
        return res;
    }


    default List<DLanguageTemplateDeclaration> getPublicTemplates(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPublicElements(getTemplateDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageTemplateDeclaration> getProtectedTemplates(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getProtectedElements(getTemplateDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

    default List<DLanguageTemplateDeclaration> getPrivateTemplates(boolean includeFromMixins, boolean includeFromInheritance, boolean includeNestedDeclarations) {
        return DUtil.getPrivateElements(getTemplateDeclarations(includeFromMixins, includeFromInheritance, includeNestedDeclarations));
    }

}
