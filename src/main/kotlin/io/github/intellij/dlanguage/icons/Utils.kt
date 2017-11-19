package io.github.intellij.dlanguage.icons

import com.intellij.ui.RowIcon
import com.intellij.util.PlatformIcons
import io.github.intellij.dlanguage.structure.Visibility
import javax.swing.Icon

fun addVisibilityToIcon(icon: Icon, visibility: Visibility) : RowIcon =
    RowIcon(icon, when (visibility) {
        Visibility.PUBLIC -> PlatformIcons.PUBLIC_ICON
        Visibility.PRIVATE -> PlatformIcons.PRIVATE_ICON
        Visibility.PROTECTED -> PlatformIcons.PROTECTED_ICON
        Visibility.PACKAGE -> PlatformIcons.PACKAGE_LOCAL_ICON
        Visibility.NONE -> PlatformIcons.PUBLIC_ICON
    })
