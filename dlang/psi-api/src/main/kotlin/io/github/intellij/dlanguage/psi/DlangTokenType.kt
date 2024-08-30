package io.github.intellij.dlanguage.psi

import com.intellij.psi.tree.IElementType
import io.github.intellij.dlanguage.DLanguage
import org.jetbrains.annotations.NonNls


class DlangTokenType(val name: @NonNls String) : IElementType(
    name, DLanguage
) {
    override fun toString(): String {
        return "DlangTokenType." + super.toString()
    }
}
