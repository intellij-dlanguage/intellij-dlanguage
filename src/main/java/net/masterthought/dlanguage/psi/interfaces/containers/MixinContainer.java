package net.masterthought.dlanguage.psi.interfaces.containers;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.Mixin;
import net.masterthought.dlanguage.psi.interfaces.Mixinable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by francis on 2/27/2017.
 * Class implemented by valid containers for a mixin
 */
public interface MixinContainer extends Container {
    Logger log = Logger.getInstance(MixinContainer.class);

    static List<Mixin> getMixinsImpl(PsiElement element, PsiElement topLevel) {
        List<Mixin> res = new ArrayList<>();
        for (PsiElement psiElement : element.getChildren()) {
            if (psiElement instanceof Mixin) {
                res.add((Mixin) psiElement);
                continue;
            }
            if (psiElement instanceof MixinContainer && topLevel != element) {
                continue;
            }
            res.addAll(getMixinsImpl(psiElement, topLevel));
        }
        return res;

    }

    @NotNull
    default List<Mixin> getMixins() {
        return getMixinsImpl(this, this);
    }

    @NotNull
    default List<Mixinable> getSymbolsFromMixins() {
        List<Mixinable> res = new ArrayList<>();
        for (Mixin mixin : getMixins()) {
            if (mixin.getTemplateDeclaration() != null)
                res.add(mixin.getTemplateDeclaration());
            else if (mixin.getTemplateMixinDeclaration() != null) {
                res.add(mixin.getTemplateMixinDeclaration());
            }
            log.debug("cannot resolve mixed in template");
        }
        return res;

    }


}
