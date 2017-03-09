package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by francis on 2/27/2017.
 * Class implemented by valid containers for a mixin
 */
public interface MixinContainer extends Container {
    @NotNull
    List<DLanguageTemplateMixin> getMixins();

    @NotNull
    List<PsiElement> getSymbolsFromMixins();


}
