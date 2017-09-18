package net.masterthought.dlanguage.psi;


import com.intellij.psi.tree.IElementType;
import net.masterthought.dlanguage.DLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class DlangTokenType extends IElementType {

    private final String debugName;

    public DlangTokenType(@NotNull @NonNls final String debugName) {
        super(debugName, DLanguage.INSTANCE);
        this.debugName = debugName;
    }

    @Override
    public String toString() {
        return "DlangTokenType." + super.toString();
    }

    public String getDebugName() {
        return debugName;
    }
}
