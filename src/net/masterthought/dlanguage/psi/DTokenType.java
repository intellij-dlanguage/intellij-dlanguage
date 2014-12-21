package net.masterthought.dlanguage.psi;

import com.intellij.psi.tree.IElementType;
import ddt.dtool.parser.DeeTokens;
import net.masterthought.dlanguage.DLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class DTokenType extends IElementType {
    public DTokenType(@NotNull @NonNls String debugName) {
        super( DeeTokens.valueOf(debugName).getSourceValue(), DLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "DTokenType." + super.toString();
    }
}

