package io.github.intellij.dlanguage.sdlang.colors

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import io.github.intellij.dlanguage.sdlang.SimpleDefinitionLanguage
import io.github.intellij.dlanguage.sdlang.highlighting.SDLangHighlighter
import javax.swing.Icon

class SDLangColorSettingsPage : ColorSettingsPage {
    private val attributes = SDLangColor.values().map { it.attributesDescriptor }.toTypedArray()

    private val annotatorTags = SDLangColor.values().associateBy({ it.name }, {it.textAttributesKey})

    private val demoTxt: String = javaClass.classLoader
        .getResource("settings/color_page.sdl")!!
        .readText()

    override fun getDisplayName(): String = SimpleDefinitionLanguage.id

    override fun getIcon(): Icon? = null

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = attributes

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getHighlighter(): SyntaxHighlighter = SDLangHighlighter()

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey> = annotatorTags

    override fun getDemoText(): String = demoTxt
}
