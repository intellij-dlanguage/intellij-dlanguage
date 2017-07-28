package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import net.masterthought.dlanguage.psi.DLanguageUnittest;
import net.masterthought.dlanguage.psi.impl.DLanguageUnittestImpl;
import net.masterthought.dlanguage.stubs.UnittestStubImpl;
import net.masterthought.dlanguage.stubs.interfaces.DLanguageUnittestStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Created by francis on 4/8/2017.
 */
public class UnittestStubElementType extends DStubElementType<DLanguageUnittestStub, DLanguageUnittest> {
    public UnittestStubElementType(@NotNull String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageUnittest createPsi(@NotNull DLanguageUnittestStub stub) {
        return new DLanguageUnittestImpl(stub, this);
    }

    @NotNull
    @Override
    public DLanguageUnittestStub createStub(@NotNull DLanguageUnittest psi, StubElement parentStub) {
        return new UnittestStubImpl(parentStub, this);
    }

    @NotNull
    @Override
    public DLanguageUnittestStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new UnittestStubImpl(parentStub, this);
    }
}
