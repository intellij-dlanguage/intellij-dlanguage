package io.github.intellij.dlanguage.psi

import com.intellij.psi.tree.IElementType
import io.github.intellij.dlanguage.DLanguage
import org.jetbrains.annotations.NonNls

class DlangElementType(debugName: @NonNls String) : IElementType(debugName, DLanguage)
