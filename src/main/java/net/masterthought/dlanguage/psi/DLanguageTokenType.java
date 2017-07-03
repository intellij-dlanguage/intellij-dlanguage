package net.masterthought.dlanguage.psi;


import com.intellij.psi.tree.IElementType;
import net.masterthought.dlanguage.DLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class DLanguageTokenType extends IElementType {

    private String debugName;

    public DLanguageTokenType(@NotNull @NonNls String debugName) {
        super(debugName, DLanguage.INSTANCE);
        this.debugName = debugName;
    }

    @Override
    public String toString() {
        return "DLanguageTokenType." + super.toString();
    }

    public String getDebugName() {
        return debugName;
    }
}
