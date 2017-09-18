package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.PsiFileStubImpl;
import net.masterthought.dlanguage.psi.DlangFile;

/**
 * Basic implementation of a stub for a D file so we can index its contents.
 */
public class DlangFileStub extends PsiFileStubImpl<DlangFile> {
    public DlangFileStub(final DlangFile file) {
        super(file);
    }
}

