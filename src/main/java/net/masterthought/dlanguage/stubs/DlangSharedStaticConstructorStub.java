package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import net.masterthought.dlanguage.psi.DLanguageSharedStaticConstructor;

/**
 * Created by francis on 1/14/2017.
 */
public class DlangSharedStaticConstructorStub extends StubBase<DLanguageSharedStaticConstructor> implements StubElement<DLanguageSharedStaticConstructor> {

    public DlangSharedStaticConstructorStub(final StubElement parent, final IStubElementType elementType) {
        super(parent, elementType);
    }
}
