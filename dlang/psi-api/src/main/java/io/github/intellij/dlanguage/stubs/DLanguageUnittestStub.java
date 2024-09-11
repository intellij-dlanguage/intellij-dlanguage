package io.github.intellij.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import io.github.intellij.dlanguage.psi.DLanguageUnittest;

/**
 * Created by francis on 4/7/2017.
 */
public class DLanguageUnittestStub extends StubBase<DLanguageUnittest> {

    public DLanguageUnittestStub(final StubElement parent, final IStubElementType elementType) {
        super(parent, elementType);
    }

}
