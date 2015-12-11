package net.masterthought.dlanguage.stubs.types;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import net.masterthought.dlanguage.DLanguage;
import net.masterthought.dlanguage.psi.DCompositeElement;
import org.jetbrains.annotations.NotNull;


public abstract class DStubElementType<S extends StubElement<T>, T extends DCompositeElement> extends IStubElementType<S, T> {
    public DStubElementType(String debugName) {
        super(debugName, DLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getExternalId() {
        return "d." + super.toString();
    }
}

