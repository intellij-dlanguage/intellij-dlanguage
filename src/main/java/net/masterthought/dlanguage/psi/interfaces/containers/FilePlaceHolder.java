package net.masterthought.dlanguage.psi.interfaces.containers;

import net.masterthought.dlanguage.psi.DLanguageFile;

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
}
