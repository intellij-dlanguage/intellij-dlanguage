package net.masterthought.dlanguage.psi.interfaces;

import com.intellij.psi.PsiElement;

/**
 * Created by francis on 3/9/2017.
 */
public interface Mixin extends PsiElement {
    Mixinable getMixinableDeclaration();

    String getName();
}
