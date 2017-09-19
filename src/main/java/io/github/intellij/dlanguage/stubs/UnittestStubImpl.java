package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import io.github.intellij.dlanguage.psi.DLanguageUnittest;
import io.github.intellij.dlanguage.stubs.interfaces.DlangUnittestStub;
import io.github.intellij.dlanguage.psi.DLanguageUnittest;
import io.github.intellij.dlanguage.stubs.interfaces.DlangUnittestStub;

/**
 * Created by francis on 4/7/2017.
 */
public class UnittestStubImpl extends StubBase<DLanguageUnittest> implements DlangUnittestStub {

    public UnittestStubImpl(final StubElement parent, final IStubElementType elementType) {
        super(parent, elementType);
    }

}
