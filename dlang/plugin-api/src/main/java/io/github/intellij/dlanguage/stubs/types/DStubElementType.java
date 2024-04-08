package io.github.intellij.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubOutputStream;
import io.github.intellij.dlanguage.DLanguage;
import io.github.intellij.dlanguage.psi.interfaces.DCompositeElement;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;


public abstract class DStubElementType<S extends StubElement<T>, T extends DCompositeElement> extends IStubElementType<S, T> {
    public DStubElementType(final String debugName) {
        super(debugName, DLanguage.INSTANCE);
    }

    @Override
    public void indexStub(@NotNull final S stub, @NotNull final IndexSink sink) {

    }

    @Override
    public void serialize(@NotNull final S stub, @NotNull final StubOutputStream dataStream) throws IOException {

    }

    @NotNull
    @Override
    public String getExternalId() {
        return "d." + super.toString();
    }

    @Override
    public boolean shouldCreateStub(final ASTNode node) {
        return true;
    }
}

