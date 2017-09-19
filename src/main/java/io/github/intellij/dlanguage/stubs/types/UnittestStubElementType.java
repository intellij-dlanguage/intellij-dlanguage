package io.github.intellij.dlanguage.stubs.types;

import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import io.github.intellij.dlanguage.psi.DLanguageUnittest;
import io.github.intellij.dlanguage.psi.impl.DlangUnittestImpl;
import io.github.intellij.dlanguage.stubs.UnittestStubImpl;
import io.github.intellij.dlanguage.stubs.interfaces.DlangUnittestStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Created by francis on 4/8/2017.
 */
public class UnittestStubElementType extends DStubElementType<DlangUnittestStub, DLanguageUnittest> {
    public UnittestStubElementType(@NotNull final String debugName) {
        super(debugName);
    }

    @Override
    public DLanguageUnittest createPsi(@NotNull final DlangUnittestStub stub) {
        return new DlangUnittestImpl(stub, this);
    }

    @NotNull
    @Override
    public DlangUnittestStub createStub(@NotNull final DLanguageUnittest psi, final StubElement parentStub) {
        return new UnittestStubImpl(parentStub, this);
    }

    @NotNull
    @Override
    public DlangUnittestStub deserialize(@NotNull final StubInputStream dataStream, final StubElement parentStub) throws IOException {
        return new UnittestStubImpl(parentStub, this);
    }
}
