package io.github.intellij.dlanguage.colors

import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.highlighting.DHighlighter

class DColorSettingsPage : ColorSettingsPage {
    private val attributes = DColor.values().map { it.attributesDescriptor }.toTypedArray()

    private val annotatorTags = DColor.values().associateBy({ it.name }, { it.textAttributesKey })

    private val demoTxt: String = javaClass.classLoader
        .getResource("settings/color_page.d")
        .readText()

    override fun getDisplayName() = DLanguage.id
    override fun getIcon() = DLanguage.Icons.FILE
    override fun getAttributeDescriptors() = attributes
    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY
    override fun getHighlighter() = DHighlighter()
    override fun getAdditionalHighlightingTagToDescriptorMap() = annotatorTags
    override fun getDemoText() = demoTxt
}
