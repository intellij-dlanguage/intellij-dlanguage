package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.PsiFileStubImpl;
import net.masterthought.dlanguage.psi.DLanguageFile;

/**
 * Basic implementation of a stub for a D file so we can index its contents.
 */
public class DLanguageFileStub extends PsiFileStubImpl<DLanguageFile> {
    public DLanguageFileStub(final DLanguageFile file) {
        super(file);
    }
}

