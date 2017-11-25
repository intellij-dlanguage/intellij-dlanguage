package io.github.intellij.dlanguage.colors

import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.intellij.openapi.util.io.StreamUtil
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.highlighting.DHighlighter
import io.github.intellij.dlanguage.icons.DlangIcons

class DColorSettingsPage : ColorSettingsPage {
    private val ATTRS = DColor.values().map { it.attributesDescriptor }.toTypedArray()

    private val ANNOTATOR_TAGS = DColor.values().associateBy({ it.name }, { it.textAttributesKey })

    private val DEMO_TEXT by lazy {
        val stream = javaClass.classLoader.getResourceAsStream("settings/color_page.d")
        StreamUtil.convertSeparators(StreamUtil.readText(stream, "UTF-8"))
    }

    override fun getDisplayName() = DLanguage.id
    override fun getIcon() = DlangIcons.FILE
    override fun getAttributeDescriptors() = ATTRS
    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY
    override fun getHighlighter() = DHighlighter()
    override fun getAdditionalHighlightingTagToDescriptorMap() = ANNOTATOR_TAGS
    override fun getDemoText() = DEMO_TEXT
}
