package io.github.intellij.dlanguage.features.documentation

import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.lang.documentation.DocumentationSettings
import com.intellij.openapi.editor.richcopy.HtmlSyntaxInfoUtil
import com.intellij.openapi.progress.ProcessCanceledException
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.elementType
import io.github.intellij.dlanguage.colors.DColor
import io.github.intellij.dlanguage.psi.DlangTypes
import io.github.intellij.dlanguage.psi.references.DReference
import io.github.intellij.dlanguage.resolve.processors.parameters.DAttributesFinder
import io.github.intellij.dlanguage.utils.*

class DSignatureDocGenerator {

    private val highlightingSaturation: Float = DocumentationSettings.getHighlightingSaturation(false)

    fun appendDeclarationHeader(builder: StringBuilder, element: PsiElement, originalElement: PsiElement) {
        val content = getDeclarationContent(element, originalElement)
        if (content.isBlank())
            return
        builder.append(DocumentationMarkup.DEFINITION_START)
        builder.append(content)
        builder.append(DocumentationMarkup.DEFINITION_END)
    }

    fun getDeclarationContent(element: PsiElement, originalElement: PsiElement): String {
        val builder = StringBuilder()
        // TODO number coloration in simple assign expressions
        // TODO handle final class, final members (fix attributes finder to support final)
        // TODO handle __gshared (fix attributes handling in general)
        when (element) {
            is FunctionDeclaration -> appendFunctionSignature(builder, element, originalElement)
            is EnumDeclaration -> appendEnumSignature(builder, element)
            is StructDeclaration -> appendStructSignature(builder, element)
            is UnionDeclaration -> appendUnionSignature(builder, element)
            is MixinTemplateDeclaration -> appendMixinTemplateDeclaration(builder, element)
            is TemplateDeclaration -> appendTemplateDeclaration(builder, element)
            is InterfaceDeclaration -> appendInterfaceSignature(builder, element)
            is ClassDeclaration -> appendClassSignature(builder, element)
            is Parameter -> appendParameterSignature(builder, element)
            is IdentifierIdentifier -> {
                appendType(builder, (element.parent as SpecifiedVariableDeclaration).type)
                builder.append(" ").append(element.identifier?.text)}
            is AutoDeclarationPart -> appendAutoDeclarationPartSignature(builder, element)
            is AliasInitializer -> appendAliasInitializerSignature(builder, element)
            is TemplateParameter -> appendTemplateParameter(builder, element)
            is EnumMember -> {
                builder.append(element.identifier!!.text)
                if (element.assignExpression != null) {
                   builder.append(" = "). append(element.assignExpression!!.text)
                }
            }
        }
        return builder.toString()
    }

    private fun getAttributesForDeclaration(element: PsiElement): DAttributesFinder? {
        var attributes: DAttributesFinder? = null
        try {
            val attributeFinder = DAttributesFinder(element)
            attributeFinder.recurseUp()
            if (element.reference != null) {
                val resolveResults = (element.reference as DReference?)!!.multiResolve(true)
                val attributesFinders: MutableSet<DAttributesFinder> = HashSet(resolveResults.size)
                if (resolveResults.size > 1) {
                    for (resolveResult in resolveResults) {
                        val dAttributesFinder = DAttributesFinder(resolveResult.element!!)
                        dAttributesFinder.recurseUp()
                        attributesFinders.add(dAttributesFinder)
                    }
                    if (attributesFinders.size == 1) {
                        attributes = attributesFinders.toTypedArray()[0]
                    }
                }
            } else {
                attributes = attributeFinder
            }
        } catch (e: ProcessCanceledException) {
            throw e
        } catch (_: Exception) { }
        return attributes
    }

    private fun appendFunctionSignature(builder: StringBuilder, element: FunctionDeclaration, originalElement: PsiElement,) {
        val attributes: DAttributesFinder? = getAttributesForDeclaration(originalElement)

        if (attributes != null) {
            builder.append(getPrefixAttributes(attributes))
        }
        if (element.type != null)
            appendType(builder, element.type)
        else if (element.autoElem != null)
            HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, element.autoElem.text, highlightingSaturation)
        builder.append(" ")
        HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.FUNCTION_DEFINITION.textAttributesKey, element.identifier?.text, highlightingSaturation)
        if (element.templateParameters != null) {
            appendTemplateParameters(builder, element.templateParameters!!)
        }
        builder.append("(")
        appendParametersSignature(builder, element.parameters)
        builder.append(")")

        if (attributes != null) {
            builder.append(getPostfixAttributes(attributes))
        }
    }


    private fun appendEnumSignature(builder: StringBuilder, element: EnumDeclaration) {
        HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "enum", highlightingSaturation)
        builder.append(" ").append(element.identifier?.text)
        if (element.type != null) {
            builder.append(" : ")
            appendType(builder, element.type)
        }
    }

    private fun appendStructSignature(builder: StringBuilder, element: StructDeclaration) {
        HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "struct", highlightingSaturation)
        builder.append(" ").append(element.identifier?.text)
        if (element.templateParameters != null) {
            appendTemplateParameters(builder, element.templateParameters!!)
        }
    }

    private fun appendUnionSignature(builder: StringBuilder, element: UnionDeclaration) {
        HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "union", highlightingSaturation)
        builder.append(" ").append(element.identifier?.text)
    }

    private fun appendClassSignature(builder: StringBuilder, element: ClassDeclaration) {
        HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "class", highlightingSaturation)
        builder.append(" ")
        builder.append(element.identifier?.text)
        builder.append(" ")
        if (element.templateParameters != null) {
            appendTemplateParameters(builder, element.templateParameters!!)
        }
        if (element.baseClassList?.baseClasss?.isNotEmpty() == true) {
            builder.append(": ")
            builder.append(element.baseClassList?.baseClasss?.joinToString(", ") { it.text })
        }
    }

    private fun appendInterfaceSignature(builder: StringBuilder, element: InterfaceDeclaration) {
        HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "interface", highlightingSaturation)
        builder.append(" ")
        builder.append(element.identifier?.text)
        builder.append(" ")
        if (element.templateParameters != null) {
            appendTemplateParameters(builder, element.templateParameters!!)
        }
        if (element.baseClassList?.baseClasss?.isNotEmpty() == true) {
            builder.append(": ")
            builder.append(element.baseClassList?.baseClasss?.joinToString(", ") { it.text })
        }
    }

    private fun appendMixinTemplateDeclaration(builder: StringBuilder, element: MixinTemplateDeclaration) {
        HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "mixin", highlightingSaturation)
        builder.append(" ")
        appendTemplateDeclaration(builder, element.templateDeclaration!!)
    }
    private fun appendTemplateDeclaration(builder: StringBuilder, element: TemplateDeclaration) {
        HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "template", highlightingSaturation)
        builder.append(" ")
        HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.FUNCTION_CALL.textAttributesKey, element.identifier!!.text, highlightingSaturation)
        appendTemplateParameters(builder, element.templateParameters!!)
        // add constraint?
    }

    private fun appendAutoDeclarationPartSignature(builder: StringBuilder, element: AutoDeclarationPart) {
        builder.append((element.parent as AutoDeclaration).storageClasss.joinToString(" "){
            HtmlSyntaxInfoUtil.getStyledSpan(DColor.KEYWORD.textAttributesKey, it.text, highlightingSaturation)
        }).append(" ").append(element.identifier?.text)
    }

    private fun appendAliasInitializerSignature(builder: StringBuilder, element: AliasInitializer) {
        HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "alias", highlightingSaturation)
        builder.append(" ")
        builder.append(element.identifier?.text)
        if (element.oP_EQ != null) {
            builder.append(" = ")
            appendType(builder, element.type!!)
        }
    }

    private fun appendParametersSignature(builder: StringBuilder, element: Parameters?) {
        element?:return
        builder.append(element.parameters.joinToString(", ") {
            val paramBuilder = StringBuilder()
            appendParameterSignature(paramBuilder, it)
            return@joinToString paramBuilder.toString()
        })
        if (element.oP_TRIPLEDOT != null)
            builder.append(", ...")
    }

    private fun appendParameterSignature(builder: StringBuilder, element: Parameter) {
        // Important: we need to take care of order for some keywords, order has meaning (return ref, ref return, ...)
        var current = element.firstChild
        var isValid = true
        while (isValid) {
            if (current !is PsiWhiteSpace)
                isValid = appendParameterKeyword(builder, current.elementType)
            current = current.nextSibling
        }
        if (element.parameterAttributes.isNotEmpty()) {
            builder.append(element.parameterAttributes.joinToString(" ") { it.text })
            builder.append(" ")
        }
        appendType(builder, element.type)
        if (element.identifier != null)
            builder.append(" ").append(element.identifier!!.text)
        if (element.assignExpression != null) {
            builder.append(" = ")
            builder.append(element.assignExpression!!.text)
        }
        if (element.oP_TRIPLEDOT != null) {
            builder.append("...")
        }
    }

    private fun appendParameterKeyword(builder: StringBuilder, element: IElementType?): Boolean {
        when (element) {
            DlangTypes.KW_SCOPE -> {
                HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "scope", highlightingSaturation)
            }
            DlangTypes.KW_CONST -> {
                HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "const", highlightingSaturation)
            }
            DlangTypes.KW_INOUT -> {
                HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "inout", highlightingSaturation)
            }
            DlangTypes.KW_SHARED -> {
                HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "shared", highlightingSaturation)
            }
            DlangTypes.KW_FINAL -> {
                HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "final", highlightingSaturation)
            }
            DlangTypes.KW_IN -> {
                HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "in", highlightingSaturation)
            }
            DlangTypes.KW_LAZY -> {
                HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "lazy", highlightingSaturation)
            }
            DlangTypes.KW_OUT -> {
                HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "out", highlightingSaturation)
            }
            DlangTypes.KW_REF -> {
                HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "ref", highlightingSaturation)
            }
            DlangTypes.KW_SCOPE -> {
                HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "scope", highlightingSaturation)
            }
            DlangTypes.KW_AUTO -> {
                HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "auto", highlightingSaturation)
            }
            DlangTypes.KW_RETURN -> {
                HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "return", highlightingSaturation)
            }
            else -> {
                return false
            }
        }
        builder.append(" ")
        return true
    }

    private fun appendTemplateParameters(builder: StringBuilder, element: TemplateParameters) {
        builder.append("(")
        builder.append(element.templateParameterList?.templateParameters?.joinToString(", ") {
            val templateBuilder = StringBuilder()
            appendTemplateParameter(templateBuilder, it)
            templateBuilder.toString()
        }?: "")
        builder.append(")")
    }

    private fun appendTemplateParameter(builder: StringBuilder, element: TemplateParameter) {
        if (element.templateAliasParameter != null) {
            HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, element.templateAliasParameter!!.kW_ALIAS!!.text, highlightingSaturation)
            builder.append(" ").append(element.templateAliasParameter!!.identifier!!.text)
        } else if (element.templateTupleParameter != null) {
            builder.append(element.templateTupleParameter!!.identifier!!.text).append("...")
        } else if (element.templateTypeParameter != null) {
            appendTemplateTypeParameter(builder, element.templateTypeParameter!!)
        } else if (element.templateThisParameter != null) {
            HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, element.templateAliasParameter!!.kW_ALIAS!!.text, highlightingSaturation)
            builder.append(" ")
            appendTemplateTypeParameter(builder, element.templateThisParameter!!.templateTypeParameter!!)
        } else if (element.templateValueParameter != null) {
            appendType(builder, element.templateValueParameter!!.type)
            builder.append(" ").append(element.templateValueParameter!!.identifier!!.text)
            if (element.templateValueParameter!!.templateValueParameterDefault != null) {
                builder.append(" = ").append(element.templateValueParameter!!.templateValueParameterDefault!!.assignExpression!!.text)
            }
        }
    }

    private fun appendTemplateTypeParameter(builder: StringBuilder, element: TemplateTypeParameter) {
        builder.append(element.identifier!!.text)
        if (element.oP_COLON != null) {
            builder.append(" : ")
            appendType(builder, element.types.first())
        }
        if (element.oP_EQ != null) {
            builder.append(" = ")
            appendType(builder, element.types.last())
        }
    }

    private fun getPrefixAttributes(attributes: DAttributesFinder): String {
        val builder = StringBuilder()
        if (attributes.isProperty()) {
            HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.AT_ATTRIBUTE.textAttributesKey, "@property", highlightingSaturation)
            builder.append("\n")
        }
        var accessibility: String? = null
        if (!attributes.isLocal()) {
            if (attributes.isPrivate()) {
                accessibility = "private"
            } else if (attributes.isPublic()) {
                accessibility = "public"
            } else if (attributes.isProtected()) {
                accessibility = "protected"
            }
        }
        if (attributes.isExtern()) {
            // FIXME can be extern (C), extern (C++), extern (D), currently it’s impossible to guess
            HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "extern", highlightingSaturation)
            if (accessibility != null)
                builder.append(" ")
        }
        if (accessibility != null) {
            HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, accessibility, highlightingSaturation)
            builder.append(" ")
        }

        return builder.toString()
    }

    private fun getPostfixAttributes(attributes: DAttributesFinder): String {
        val builder = StringBuilder()
        if (attributes.isConst()) {
            HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "const", highlightingSaturation)
            builder.append(" ")
        }
        if (attributes.isImmutable()) {
            HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "immutable", highlightingSaturation)
            builder.append(" ")
        }
        if (attributes.isPure()) {
            HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "pure", highlightingSaturation)
            builder.append(" ")
        }
        if (attributes.isNothrow()) {
            HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "nothrow", highlightingSaturation)
            builder.append(" ")
        }
        if (attributes.isNoGC()) {
            HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.AT_ATTRIBUTE.textAttributesKey, "nogc", highlightingSaturation)
            builder.append(" ")
        }
        // TODO enable when safety will be supported by attributes finder
        //HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.AT_ATTRIBUTE.textAttributesKey, "@" + attributes.getSafety(), highlightingSaturation)

        return builder.trim().toString()
    }

    private fun appendType(builder: StringBuilder, element: Type?): StringBuilder {
        element?:return builder

        if (element.basicType != null) {
            // Prefix keywords
            if (element.basicType!!.kW_CONST != null) {
                HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "const", highlightingSaturation)
            }
            if (element.basicType!!.kW_INOUT != null) {
                HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "inout", highlightingSaturation)
            }
            if(element.basicType!!.oP_PAR_LEFT != null) {
                builder.append("(")
            }

            // type itself
            if (element.basicType!!.builtinType != null) {
                HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, element.basicType!!.builtinType!!.text, highlightingSaturation)
            } else if (element.basicType!!.type != null) {
                appendType(builder, element.basicType!!.type)
            } else if (element.basicType!!.typeIdentifierPart != null) {
                appendTypeIdentifierPart(builder, element.basicType!!.typeIdentifierPart!!)
            }

            if(element.basicType!!.oP_PAR_RIGHT != null) {
                builder.append(")")
            }

            // suffix
            if (element.typeSuffixs.isNotEmpty()) {
                if (element.typeSuffixs.size > 1 &&
                    (element.typeSuffixs.first().oP_ASTERISK == null && element.typeSuffixs.first().oP_BRACKET_LEFT == null)) {
                    // keep star and [ attached to their type
                    builder.append(" ")
                }
                builder.append(element.typeSuffixs.joinToString("") {
                    val parametersBuilder = StringBuilder()
                    if (it.kW_DELEGATE != null) {
                        HtmlSyntaxInfoUtil.appendStyledSpan(parametersBuilder, DColor.KEYWORD.textAttributesKey, "delegate", highlightingSaturation)
                    }
                    if (it.kW_FUNCTION != null) {
                        HtmlSyntaxInfoUtil.appendStyledSpan(parametersBuilder, DColor.KEYWORD.textAttributesKey, "function", highlightingSaturation)
                    }
                    if (it.parameters != null) {
                        parametersBuilder.append("(")
                        appendParametersSignature(parametersBuilder, it.parameters)
                        parametersBuilder.append(")")
                        return@joinToString parametersBuilder.toString()
                    }
                    return@joinToString it.text
                })
            }
        } else {
            builder.append(element.basicType!!.text)
            if (element.typeSuffixs.isNotEmpty()) {
                builder.append(element.typeSuffixs.joinToString("") { it.text })
            }
        }

        return builder
    }

    private fun appendTypeIdentifierPart(builder: StringBuilder, element: TypeIdentifierPart) {
        if (element.identifierOrTemplateInstance != null) {
            if (element.identifierOrTemplateInstance!!.identifier != null) {
                builder.append(element.identifierOrTemplateInstance!!.identifier!!.text)
            }
            else {
                // template instance
                builder.append(element.identifierOrTemplateInstance!!.templateInstance!!.identifier!!.text)
                if (element.identifierOrTemplateInstance!!.templateInstance!!.templateArguments?.templateSingleArgument != null) {
                    builder.append("!")
                    appendTemplateSingleArgument(builder, element.identifierOrTemplateInstance!!.templateInstance!!.templateArguments!!.templateSingleArgument!!)
                }
                else {
                    builder.append("!(")
                    appendTemplateArguments(builder, element.identifierOrTemplateInstance!!.templateInstance!!.templateArguments!!)
                    builder.append(")")
                }
            }

            if (element.oP_BRACKET_LEFT != null)
                builder.append("[")
            if (element.assignExpression != null)
                builder.append(element.assignExpression!!.text)
            if (element.oP_BRACKET_RIGHT != null)
                builder.append("]")

            if (element.oP_DOT != null) {
                builder.append(".")
                appendTypeIdentifierPart(builder, element.typeIdentifierPart!!)
            }
        } else {
            builder.append(element.text)
        }
    }

    private fun appendTemplateArguments(builder: StringBuilder, element: TemplateArguments) {
        builder.append(element.templateArgumentList!!.templateArguments.joinToString(", ") {
            val argumentBuilder = StringBuilder()
            appendType(argumentBuilder, it.type)
            if (it.assignExpression != null)
                argumentBuilder.append(it.assignExpression!!.text)
            argumentBuilder.toString()
        })

    }

    private fun appendTemplateSingleArgument(builder: StringBuilder, element: TemplateSingleArgument) {
        if (element.kW_FALSE != null)
            HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "false", highlightingSaturation)
        else if (element.kW_TRUE != null)
            HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "true", highlightingSaturation)
        else if (element.kW_NULL != null)
            HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "null", highlightingSaturation)
        else if (element.kW_SUPER != null)
            HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "super", highlightingSaturation)
        else if (element.kW_THIS != null)
            HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, "this", highlightingSaturation)
        else if (element.builtinType != null)
            HtmlSyntaxInfoUtil.appendStyledSpan(builder, DColor.KEYWORD.textAttributesKey, element.builtinType!!.text, highlightingSaturation)
        else
            builder.append(element.text)
    }

}
