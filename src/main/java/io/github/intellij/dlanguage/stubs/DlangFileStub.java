package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.PsiFileStubImpl;
import io.github.intellij.dlanguage.psi.DlangPsiFileImpl;

/**
 * Basic implementation of a stub for a D file so we can index its contents.
 */
public class DlangFileStub extends PsiFileStubImpl<DlangPsiFileImpl> {
    public DlangFileStub(final DlangPsiFileImpl file) {
        super(file);
    }
}

