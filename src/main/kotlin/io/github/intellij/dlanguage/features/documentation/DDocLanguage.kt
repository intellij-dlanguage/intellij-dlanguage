package io.github.intellij.dlanguage.features.documentation

import com.intellij.lang.DependentLanguage
import com.intellij.lang.Language
import io.github.intellij.dlanguage.DLanguage

object DDocLanguage : Language(DLanguage, "DDoc"), DependentLanguage {
}
