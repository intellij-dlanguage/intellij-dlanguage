package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.PsiFileStubImpl;
import net.masterthought.dlanguage.psi.DlangFile;

/**
 * Basic implementation of a stub for a D file so we can index its contents.
 */
public class DLanguageFileStub extends PsiFileStubImpl<DlangFile> {
    public DLanguageFileStub(final DlangFile file) {
        super(file);
    }
}

