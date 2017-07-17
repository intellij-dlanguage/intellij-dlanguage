package net.masterthought.dlanguage.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubOutputStream;
import net.masterthought.dlanguage.DLanguage;
import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;


public abstract class DStubElementType<S extends StubElement<T>, T extends DCompositeElement> extends IStubElementType<S, T> {
    public DStubElementType(String debugName) {
        super(debugName, DLanguage.INSTANCE);
    }

    @Override
    public void indexStub(@NotNull S stub, @NotNull IndexSink sink) {

    }

    @Override
    public void serialize(@NotNull S stub, @NotNull StubOutputStream dataStream) throws IOException {

    }

    @NotNull
    @Override
    public String getExternalId() {
        return "d." + super.toString();
    }

    @Override
    public boolean shouldCreateStub(ASTNode node) {
        return true;
    }
}

