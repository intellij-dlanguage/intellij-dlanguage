package io.github.intellij.dlanguage.features

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.features.documentation.DDocGenerator
import io.github.intellij.dlanguage.features.documentation.psi.DlangDocComment
import io.github.intellij.dlanguage.psi.DlangFile
import io.github.intellij.dlanguage.psi.impl.named.DlangSingleImportImpl
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.psi.named.DlangSingleImport
import io.github.intellij.dlanguage.psi.references.DReference
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributesFinder
import java.util.function.Consumer

/**
 * Created by francis on 7/18/2017.
 */
class DDocumentationProvider : AbstractDocumentationProvider() {

    /**
     * Returns the text to show in the Ctrl-hover popup for the specified element.
     *
     * @param element         the element for which the documentation is requested (for example, if the mouse is over
     * a method reference, this will be the method to which the reference is resolved).
     * @param originalElement the element under the mouse cursor
     * @return the documentation to show, or null if the provider can't provide any documentation for this element.
     */
    override fun getQuickNavigateInfo(element: PsiElement?, originalElement: PsiElement?): String? = null

    /*
    * Returns the list of possible URLs to show as external documentation for the specified element.
    * The URL's are shown in UI by clicking on 'View' -> 'ExternalDocumentation', or by using Ctrl+F1
    *
    * This is different to the documentation that is displayed on hover.
    *
    * todo: Add further logic to actually return other useful results from dlang.org (phobos or language spec)
    *  https://dlang.org/spec/spec.html
    *  https://dlang.org/phobos/index.html
    */
    override fun getUrlFor(element: PsiElement?, originalElement: PsiElement?): List<String> {
        originalElement?.let {
            val singleImport = PsiTreeUtil.findFirstParent(it, true) { t -> t is DlangSingleImport }

            val moduleName = (singleImport as DlangSingleImportImpl?)?.importedModuleName

            return if (moduleName?.startsWith("std.") == true)
                arrayListOf("https://dlang.org/phobos/${moduleName.replace('.', '_')}.html")
            else
                emptyList()
        }

        return emptyList()
    }

    override fun collectDocComments(file: PsiFile, sink: Consumer<in PsiDocCommentBase>) {
        if (file !is DlangFile) return
        for (element in SyntaxTraverser.psiTraverser(file)) {
            if (element is DlangDocComment) {
                sink.accept(element)
            }
        }
    }

    override fun generateRenderedDoc(comment: PsiDocCommentBase): String? {
        if (comment !is DlangDocComment)
            return super.generateRenderedDoc(comment)
        return DDocGenerator().generateDocRendered(comment)
    }

    /**
     *
     * Callback for asking the doc provider for the complete documentation.
     * Underlying implementation may be time-consuming, that's why this method is expected not to be called from EDT.
     *
     *
     * One can use [com.intellij.lang.documentation.DocumentationMarkup] to get proper content layout. Typical sample will look like this:
     * <pre>
     * DEFINITION_START + definition + DEFINITION_END +
     * CONTENT_START + main description + CONTENT_END +
     * SECTIONS_START +
     * SECTION_HEADER_START + section name +
     * SECTION_SEPARATOR + "&lt;p&gt;" + section content + SECTION_END +
     * ... +
     * SECTIONS_END
     * </pre>
     *
     * To show different content on mouse hover in editor, [.generateHoverDoc] should be implemented.
     *
     * @param element         the element for which the documentation is requested (for example, if the mouse is over
     * a method reference, this will be the method to which the reference is resolved).
     * @param originalElement the element under the mouse cursor
     * @return target element's documentation, or `null` if provider is unable to generate documentation
     * for the given element
     */
    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): String? {
        // todo: check out JavaDocumentationProvider for some ideas. Depending on the PsiElement being passed in
        // we should probably generate the documentation in different formats
        if (element is DNamedElement && element.getParent() is DNamedElement) {
            try {
                var a = DAttributesFinder(element)
                a.recurseUp()
                if (element.getReference() != null) {
                    val resolveResults = (element.getReference() as DReference?)!!.multiResolve(true)
                    val attributesFinders: MutableSet<DAttributesFinder> = HashSet(resolveResults.size)
                    if (resolveResults.size > 1) {
                        for (resolveResult in resolveResults) {
                            val dAttributesFinder = DAttributesFinder(resolveResult.element!!)
                            dAttributesFinder.recurseUp()
                            attributesFinders.add(dAttributesFinder)
                        }
                        a = if (attributesFinders.size == 1) {
                            attributesFinders.toTypedArray()[0]
                        } else return "Unable to resolve symbol to one declaration"
                    }
                }
                return if (element == originalElement && element.getReference() != null) {
                    "Unable to resolve symbol to one declaration"
                } else buildDocumentationString(a)
            } catch (e: Exception) {
                LOG.error("Could not generate documentation", e)
            }
        }
        return null
    }

    override fun getDocumentationElementForLookupItem(
        psiManager: PsiManager,
        `object`: Any,
        element: PsiElement
    ): PsiElement? {
        return null
    }

    override fun getDocumentationElementForLink(
        psiManager: PsiManager,
        link: String,
        context: PsiElement
    ): PsiElement? {
        return null
    }

    /*
    * This method builds up the html text that will be used for tooltip content in the IDE while hovering over variables and
    * function names.
    *
    * Use the html markup as specified in DocumentationMarkup
    */
    private fun buildDocumentationString(a: DAttributesFinder): String {
        val sb = StringBuilder()
        sb.append(DocumentationMarkup.DEFINITION_START)
        if (!a.isLocal()) {
            if (a.isPrivate()) {
                sb.append("private")
            } else if (a.isPublic()) {
                sb.append("public")
            } else if (a.isProtected()) {
                sb.append("protected")
            }
            sb.append(" ")
        }
        sb.append("symbol").append(" ")

        //sb.append(CONTENT_START);
        if (a.isProperty()) {
            sb.append(DocumentationMarkup.GRAYED_START).append("property").append(DocumentationMarkup.GRAYED_END)
                .append(" ")
        }
        if (a.isStatic()) {
            sb.append(DocumentationMarkup.GRAYED_START).append("static").append(DocumentationMarkup.GRAYED_END)
                .append(" ")
        }
        if (a.isExtern()) {
            sb.append(DocumentationMarkup.GRAYED_START).append("extern").append(DocumentationMarkup.GRAYED_END)
                .append(" ")
        }
        if (a.isNoGC()) {
            sb.append(DocumentationMarkup.GRAYED_START).append("nogc").append(DocumentationMarkup.GRAYED_END)
                .append(" ")
        }
        if (a.isLocal()) {
            sb.append(DocumentationMarkup.GRAYED_START).append("local").append(DocumentationMarkup.GRAYED_END)
                .append(" ")
        }

        //sb.append(CONTENT_END);
        sb.append(DocumentationMarkup.DEFINITION_END)
        return sb.toString()
    }

    companion object {
        private val LOG = Logger.getInstance(
            DDocumentationProvider::class.java
        )
    }
}
