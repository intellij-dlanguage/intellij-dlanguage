package net.masterthought.dlanguage.stubs;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import net.masterthought.dlanguage.psi.DLanguageUnitTesting;
import net.masterthought.dlanguage.stubs.interfaces.UnitTestingStub;

/**
 * Created by francis on 4/7/2017.
 */
public class UnitTestingStubImpl extends StubBase<DLanguageUnitTesting> implements UnitTestingStub {

    public UnitTestingStubImpl(StubElement parent, IStubElementType elementType) {
        super(parent, elementType);
    }

}
