package net.masterthought.dlanguage.psi;


import com.intellij.psi.tree.IElementType;
import net.masterthought.dlanguage.DLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class DLanguageTokenType extends IElementType {

    public DLanguageTokenType(@NotNull @NonNls final String debugName) {
        super(debugName, DLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "DLanguageTokenType." + super.toString();
    }

}
