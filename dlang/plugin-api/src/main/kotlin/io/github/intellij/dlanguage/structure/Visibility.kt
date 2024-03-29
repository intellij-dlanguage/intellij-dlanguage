package io.github.intellij.dlanguage.structure

import io.github.intellij.dlanguage.psi.DLanguageAttribute

enum class Visibility {
    NONE,
    PUBLIC,
    PRIVATE,
    PROTECTED,
    PACKAGE,
}

fun fromPsiAttribute(attribute: DLanguageAttribute): Visibility {
    if (attribute.kW_PUBLIC != null) {
        return Visibility.PUBLIC
    }

    if (attribute.kW_PRIVATE != null) {
        return Visibility.PRIVATE
    }

    if (attribute.kW_PROTECTED != null) {
        return Visibility.PROTECTED
    }

    if (attribute.kW_PACKAGE != null) {
        return Visibility.PACKAGE
    }

    return Visibility.NONE
}
