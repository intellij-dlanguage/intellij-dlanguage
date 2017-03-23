package net.masterthought.dlanguage.psi.interfaces.containers;

import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.DLanguageFile;
import net.masterthought.dlanguage.psi.references.placeholders.PlaceHolder;

/**
 * Created by francis on 3/19/2017.
 */
public class FilePlaceHolder implements PlaceHolder {
    private DLanguageFile file;

    public FilePlaceHolder(DLanguageFile file) {
        this.file = file;
    }

    public DLanguageFile getFile() {
        return file;
    }

    @Override
    public PsiElement getElement() {
        return file;
    }
}
