package io.github.intellij.dlanguage.psi;

import com.intellij.psi.tree.IElementType;
import io.github.intellij.dlanguage.DLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class DlangElementType extends IElementType {

    public DlangElementType(@NotNull @NonNls final String debugName) {
        super(debugName, DLanguage.INSTANCE);
    }
}
