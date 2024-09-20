package io.github.intellij.dlanguage.psi.interfaces

import com.intellij.psi.PsiElement

// TODO move this comment to the relevant place (in the visibilityLookup)
// Note: local symbols (declaration in DeclarationStatements) cannot have an attribute, hence his visibility is private
// TODO add this interface to more declarations that have visibility
interface DVisibilityModifier : PsiElement {
    fun visibility(): DVisibility
}

/**
 * Represent the visibility of a symbol.
 * @see <a href="https://dlang.org/spec/attribute.html#visibility_attributes">Visibility Attributes</a>
 */
sealed class DVisibility {
    object Export : DVisibility()
    object Public : DVisibility()
    object Private : DVisibility()
    object Protected : DVisibility()
    data class Package(val name: String?) : DVisibility()
}

enum class DVisibilityStubKind {
    EXPORT,
    PUBLIC,
    PRIVATE,
    PROTECTED,
    PACKAGE,
    PACKAGE_SPECIFIC
}

val DVisibility.stubKind: DVisibilityStubKind
    get() = when(this) {
        DVisibility.Export -> DVisibilityStubKind.EXPORT
        DVisibility.Public -> DVisibilityStubKind.PUBLIC
        DVisibility.Private -> DVisibilityStubKind.PRIVATE
        DVisibility.Protected -> DVisibilityStubKind.PROTECTED
        is DVisibility.Package -> {
            if (this.name != null)
                DVisibilityStubKind.PACKAGE_SPECIFIC
            DVisibilityStubKind.PACKAGE
        }
        else -> error("Unsupported visibility")
    }
