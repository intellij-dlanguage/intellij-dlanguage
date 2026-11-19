package io.github.intellij.dlanguage.features

import com.intellij.codeInsight.documentation.DocumentationManagerUtil
import com.intellij.codeInsight.navigation.fileLocation
import com.intellij.codeInsight.navigation.fileStatusAttributes
import com.intellij.diagnostic.PluginException
import com.intellij.lang.documentation.DocumentationMarkup.BOTTOM_ELEMENT
import com.intellij.model.Pointer
import com.intellij.navigation.ColoredItemPresentation
import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.util.Iconable
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.text.HtmlChunk
import com.intellij.openapi.vfs.newvfs.VfsPresentationUtil
import com.intellij.platform.backend.documentation.*
import com.intellij.platform.backend.presentation.TargetPresentation
import com.intellij.pom.Navigatable
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.createSmartPointer
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.childrenOfType
import io.github.intellij.dlanguage.DLanguage
import io.github.intellij.dlanguage.documentation.psi.DlangDocComment
import io.github.intellij.dlanguage.features.documentation.DDocGenerator
import io.github.intellij.dlanguage.features.documentation.DSignatureDocGenerator
import io.github.intellij.dlanguage.psi.DlangPsiFile
import io.github.intellij.dlanguage.psi.interfaces.Declaration
import org.jetbrains.annotations.Nls
import java.util.regex.Pattern

internal val log = Logger.getInstance("#io.github.intellij.dlanguage.DlangDocumentationTarget")

class DPsiDocumentationTarget : PsiDocumentationTargetProvider {

    override fun documentationTarget(element: PsiElement, originalElement: PsiElement?): DocumentationTarget? {
        return if (element.language == DLanguage) {
            DDocumentationTarget(element, originalElement)
        } else {
            null
        }
    }
}

private class DInlineDocumentation(private val comments: List<DlangDocComment>, private val declaration: Declaration): InlineDocumentation {
    override fun getDocumentationOwnerRange(): TextRange? {
        return declaration.textRange
    }

    override fun getDocumentationRange(): TextRange {
        assert(comments.isNotEmpty())
        return comments.first().textRange
    }

    override fun getOwnerTarget(): DocumentationTarget {
        return DDocumentationTarget(declaration, declaration)
    }

    override fun renderText(): @Nls String {
        assert(comments.isNotEmpty())
        return DDocGenerator().generateDocRendered(comments.first()) // TODO use all comments concatenated
    }
}

class DInlineDocumentationProvider: InlineDocumentationProvider {
    override fun inlineDocumentationItems(file: PsiFile?): Collection<InlineDocumentation> {
        if (file !is DlangPsiFile) return emptyList()

        val result = mutableListOf<InlineDocumentation>()
        PsiTreeUtil.processElements(file) {
            val declaration = it as? Declaration
            val comments = declaration?.childrenOfType<DlangDocComment>()
            if (!comments.isNullOrEmpty()) {
                result.add(DInlineDocumentation(comments, declaration))
            }
            true
        }
        return result
    }

    override fun findInlineDocumentation(
        file: PsiFile,
        textRange: TextRange
    ): InlineDocumentation? {
        val comment = PsiTreeUtil.getParentOfType(file.findElementAt(textRange.startOffset), DlangDocComment::class.java) ?: return null
        if (comment.textRange == textRange) {
            val declaration = comment.owner as? Declaration ?: return null
            val comments = declaration.childrenOfType<DlangDocComment>()
            if (comments.isEmpty()) return null
            return DInlineDocumentation(comments, declaration)
        }
        return null
    }

}

internal class DDocumentationTarget(val element: PsiElement, val originalElement: PsiElement?) : DocumentationTarget {
    override fun createPointer(): Pointer<out DocumentationTarget> {
        val elementPtr = element.createSmartPointer()
        val originalPtr = originalElement?.createSmartPointer()

        return Pointer {
            val element = elementPtr.dereference() ?: return@Pointer null
            DDocumentationTarget(element, originalPtr?.dereference())
        }
    }

    override fun computePresentation(): TargetPresentation {
        val project = element.project
        val file = element.containingFile?.virtualFile
        val itemPresentation = (element as? NavigationItem)?.presentation
        val presentableText: String = itemPresentation?.presentableText
            ?: (element as? PsiNamedElement)?.name
            ?: element.text
            ?: run {
                log.error(PluginException.createByClass("${element.javaClass.name} cannot be presented", null, element.javaClass))
                element.toString()
            }
        val fileTextWithIcon = if (file != null) fileLocation(project, file) else null
        return TargetPresentation
            .builder(presentableText)
            .backgroundColor(file?.let { VfsPresentationUtil.getFileBackgroundColor(project, file)})
            .icon(element.getIcon(Iconable.ICON_FLAG_VISIBILITY or Iconable.ICON_FLAG_READ_STATUS))
            .presentableTextAttributes(itemPresentation?.getColoredAttributes())
            .containerText(itemPresentation?.getContainerText(), file?.let { fileStatusAttributes(project, file) })
            .locationText(fileTextWithIcon?.text, fileTextWithIcon?.icon)
            .presentation()
    }

    override val navigatable : Navigatable?
        get() = element as? Navigatable


    override fun computeDocumentationHint(): String? {
        val builder = StringBuilder()
        DSignatureDocGenerator().appendDeclarationHeader(builder, element, element)
        val containerInfo = getContainerInfo(element).toString()
        if (containerInfo.isNotBlank()) {
            builder.append(BOTTOM_ELEMENT.addRaw(containerInfo).toString())
        }
        val result = builder.toString()
        return result.ifBlank { null }
    }

    override fun computeDocumentation(): DocumentationResult? {
        val builder = StringBuilder()
        DSignatureDocGenerator().appendDeclarationHeader(builder, element, element)
        val doc = DDocGenerator().generateDoc(element)
        builder.append(doc)
        val containerInfo = getContainerInfo(element).toString()
        if (containerInfo.isNotBlank()) {
            builder.append(BOTTOM_ELEMENT.addRaw(containerInfo).toString())
        }
        val result = builder.toString()
        return if (result.isBlank()) null else DocumentationResult.documentation(result)
    }

}

private fun ItemPresentation.getColoredAttributes(): TextAttributes? {
    val coloredPresentation = this as? ColoredItemPresentation
    val textAttributesKey = coloredPresentation?.textAttributesKey ?: return null
    return EditorColorsManager.getInstance().schemeForCurrentUITheme.getAttributes(textAttributesKey)
}

private val CONTAINER_PATTERN: Pattern = Pattern.compile("(\\(in |\\()?([^)]*)(\\))?")

@Nls
private fun ItemPresentation.getContainerText(): String? {
    val locationString = locationString ?: return null
    val matcher = CONTAINER_PATTERN.matcher(locationString)
    return if (matcher.matches()) matcher.group(2) else locationString
}

private fun getContainerInfo(element: PsiElement): HtmlChunk {
    val fqName = (element.containingFile as? DlangPsiFile)?.getFullyQualifiedModuleName()?.takeIf { it.contains('.') }

    val fqNameSection = fqName?.let {
        val link = StringBuilder()
        DocumentationManagerUtil.createHyperlink(link, it, it, false)
        HtmlChunk.fragment(
            HtmlChunk.tag("icon").attr(
                "src",
                "AllIcons.Nodes.Package"
            ),
            HtmlChunk.nbsp(),
            HtmlChunk.raw(link.toString()),
            HtmlChunk.br()
        )
    } ?: HtmlChunk.empty()

    val fileNameSection = element.navigationElement.containingFile
        ?.name
        ?.let {
            HtmlChunk.fragment(
                HtmlChunk.tag("icon").attr("src", "AllIcons.Nodes.Package"), // TODO Dlang File
                HtmlChunk.nbsp(),
                HtmlChunk.text(it),
                HtmlChunk.br()
            )
        }
        ?: HtmlChunk.empty()

    return HtmlChunk.fragment(fqNameSection, fileNameSection)
}
