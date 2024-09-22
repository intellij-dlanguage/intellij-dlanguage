package io.github.intellij.dlanguage.psi.interfaces;

import io.github.intellij.dlanguage.psi.types.DType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface DTypedElement {

    @NotNull
    @Contract(pure = true)
    DType getDType();
}
