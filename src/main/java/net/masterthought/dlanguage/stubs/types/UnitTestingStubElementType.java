package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import net.masterthought.dlanguage.psi.DLanguageUnitTesting;
import net.masterthought.dlanguage.psi.impl.DLanguageUnitTestingImpl;
import net.masterthought.dlanguage.stubs.UnitTestingStubImpl;
import net.masterthought.dlanguage.stubs.interfaces.UnitTestingStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Created by francis on 4/8/2017.
 */
public class UnitTestingStubElementType extends DStubElementType<UnitTestingStub, DLanguageUnitTesting> {
    public UnitTestingStubElementType(@NotNull String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageUnitTesting createPsi(@NotNull UnitTestingStub stub) {
        return new DLanguageUnitTestingImpl(stub, this);
    }

    @NotNull
    @Override
    public UnitTestingStub createStub(@NotNull DLanguageUnitTesting psi, StubElement parentStub) {
        return new UnitTestingStubImpl(parentStub, this);
    }

    @NotNull
    @Override
    public UnitTestingStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new UnitTestingStubImpl(parentStub, this);
    }
}
