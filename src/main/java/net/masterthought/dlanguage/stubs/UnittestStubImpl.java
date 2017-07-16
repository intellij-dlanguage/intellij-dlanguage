package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import net.masterthought.dlanguage.psi.DLanguageUnittest;
import net.masterthought.dlanguage.stubs.interfaces.DLanguageUnittestStub;

/**
 * Created by francis on 4/7/2017.
 */
public class UnittestStubImpl extends StubBase<DLanguageUnittest> implements DLanguageUnittestStub {

    public UnittestStubImpl(StubElement parent, IStubElementType elementType) {
        super(parent, elementType);
    }

}
