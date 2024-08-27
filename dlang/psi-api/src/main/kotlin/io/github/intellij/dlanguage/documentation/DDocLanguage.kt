package io.github.intellij.dlanguage.documentation

import com.intellij.lang.DependentLanguage
import com.intellij.lang.Language
import io.github.intellij.dlanguage.DLanguage

object DDocLanguage : Language(DLanguage, "DDoc"), DependentLanguage {
}
