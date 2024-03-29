package io.github.intellij.dlanguage.psi;


import com.intellij.psi.tree.IElementType;
import io.github.intellij.dlanguage.DLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class DlangTokenType extends IElementType {

    private final String name;

    public DlangTokenType(@NotNull @NonNls final String debugName) {
        super(debugName, DLanguage.INSTANCE);
        this.name = debugName;
    }

    @Override
    public String toString() {
        return "DlangTokenType." + super.toString();
    }

    public String getName() {
        return name;
    }
}
