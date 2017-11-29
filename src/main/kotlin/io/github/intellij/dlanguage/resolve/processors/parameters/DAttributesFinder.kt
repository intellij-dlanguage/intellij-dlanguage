package io.github.intellij.dlanguage.resolve.processors.parameters

import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.psi.*
import io.github.intellij.dlanguage.utils.*

/**
 * Created by francis on 7/17/2017.
 */
class DAttributesFinder {

    val startingPoint: PsiElement

    constructor(startingPoint: PsiElement) {
        this.startingPoint = startingPoint
        if (startingPoint is Constructor) {
            handleConstructor()
//                recurseUpImpl(startingPoint.kW_THIS!!)
        } else if (startingPoint is FunctionDeclaration) {
            handleFunctionDeclaration()
//                recurseUpImpl(startingPoint.identifier!!)
        } else if (startingPoint is InterfaceOrClass) {
            handleInterfaceOrClass()
//                recurseUpImpl(startingPoint.identifier!!)
        } else if (startingPoint is UnionDeclaration) {
            handleUnionDeclaration()
//                recurseUpImpl(startingPoint.identifier!!)
        } else if (startingPoint is StructDeclaration) {
            handleStructDeclaration()
//                recurseUpImpl(startingPoint.identifier!!)
        } else if (startingPoint is LabeledStatement) {
            handleLabeledStatement()
//                recurseUpImpl(startingPoint.identifier!!)
        } else if (startingPoint is AutoDeclarationPart) {
            handleAutoDeclarationPart()
//                recurseUpImpl(startingPoint.identifier!!)
        } else if (startingPoint is EnumDeclaration) {
            handleEnumDeclaration()
//                recurseUpImpl(startingPoint.identifier!!)
        } else if (startingPoint is Catch) {
            handleCatch()
//                recurseUpImpl(startingPoint.identifier!!)
        } else if (startingPoint is Declarator) {
            handleDeclarator()
//                recurseUpImpl(startingPoint.identifier!!)
        } else if (startingPoint is EponymousTemplateDeclaration) {
            handleEponymousTemplateDeclaration()
//                recurseUpImpl(startingPoint.identifier!!)
        } else if (startingPoint is ForeachType) {
            handleForeachType()
//                recurseUpImpl(startingPoint.identifier!!)
        } else if (startingPoint is DLanguageIfCondition) {
            handleDLanguageIfCondition()
//                recurseUpImpl(startingPoint.identifier!!)
        } else if (startingPoint is TemplateDeclaration) {
            handleTemplateDeclaration()
//                recurseUpImpl(startingPoint.identifier!!)
        }

    }

    enum class Visibility {
        PUBLIC, PRIVATE, PROTECTED, LOCAL
    }

    private var visibility: Visibility? = null
    private var isStatic: Boolean? = null
    private var isProperty: Boolean? = null
    private var isNoGC: Boolean? = null
    private var isExtern: Boolean? = null
    private var isPure: Boolean? = null
    private var isNothrow: Boolean? = null
    private var isConst: Boolean? = null
    private var isImmutable: Boolean? = null

    private var visibilityByBulkAttributeApplication: Visibility? = null
    private var isStaticByBulkAttributeApplication: Boolean? = null
    private var isPropertyByBulkAttributeApplication: Boolean? = null
    private var isNoGCByBulkAttributeApplication: Boolean? = null
    private var isExternByBulkAttributeApplication: Boolean? = null
    private var isPureByBulkAttributeApplication: Boolean? = null
    private var isNothrowByBulkAttributeApplication: Boolean? = null
    private var isConstByBulkAttributeApplication: Boolean? = null
    private var isImmutableByBulkAttributeApplication: Boolean? = null

    var defaultsToStatic: Boolean = true
    var defualtVisibility = Visibility.PUBLIC
    var defaultsToProperty: Boolean = false
    var defaultsToNoGC: Boolean = false
    var defaultsToExtern: Boolean = false
    var defaultsToLocal: Boolean = false
    var defaultsToPure: Boolean = false
    var defaultsToNothrow: Boolean = false
    var defaultsToConst: Boolean = false
    var defaultsToImmutable: Boolean = false


    fun recurseUp() {
        // make sure that the starting point is an identifier

        recurseUpImpl(startingPoint)
    }

    private fun recurseUpImpl(startingPoint: PsiElement) {
        var point = startingPoint
        while (true) {
            while (true) {
                if (!execute(point)) {
                    return
                }
                if (point.prevSibling == null) {
                    break
                }
                point = point.prevSibling
            }
            if (point.parent == null) {
                break
            }
            point = point.parent
        }
    }

    fun isParent(parent: PsiElement, child: PsiElement): Boolean {
        if (child == parent)
            return true
        if (child is DlangFile) {
            return false
        }
        return isParent(parent, child.parent)
    }

    fun execute(element: PsiElement): Boolean {
        if (element is DlangSingleImport && isParent(element, startingPoint)) {
            defaultsToStatic = false
        }
        if (element is FunctionDeclaration || element is DLanguageUnittest || element is Parameters || element is TemplateParameters) {
            if (element is FunctionDeclaration) {
                if (element.memberFunctionAttributes != null) {
                    execute(element.memberFunctionAttributes)
                }
                if ((element.functionBody != null && isParent(element.functionBody!!, startingPoint))
                    || (element.parameters != null && isParent(element.parameters!!, startingPoint))
                    || (element.templateParameters != null && isParent(element.templateParameters!!, startingPoint))) {
                    visibility = Visibility.LOCAL
                    return false
                }
            }
            return true
        }
        if (element is StructBody) {
            if (isParent(element, startingPoint)) {
                defaultsToStatic = false
                return false
            }
        }
        if (element is Attribute) {
            updateFromAttribute(element)
        }
        if (element is Declaration) {
            if (element.attributeDeclaration != null) {
                for (attribute in element.attributes) {
                    updateFromAttribute(attribute)
                }
            }
        }
        if (element is AttributeDeclaration) {
            updateFromAttribute(element.attribute!!)
        }
        return true
    }

    private fun execute(elements: List<DLanguageMemberFunctionAttribute>) {
        for (element in elements) {
            if (element.text.equals("const")) {
                isConst = true
            }
        }
    }

    fun updateFromAttribute(attribute: DLanguageAttribute) {
        if (attribute.textOffset < startingPoint.textOffset) {

            if (attribute.kW_EXPORT != null) {
            } else if (attribute.kW_PACKAGE != null) {
            } else if (attribute.kW_PRIVATE != null) {
                if (visibility == null) {
                    visibility = Visibility.PRIVATE
                }
            } else if (attribute.kW_PROTECTED != null) {
                if (visibility == null) {
                    visibility = Visibility.PROTECTED
                }
            } else if (attribute.kW_PUBLIC != null) {
                if (visibility == null) {
                    visibility = Visibility.PUBLIC
                }
            } else if (attribute.pragmaExpression != null) {
            } else if (attribute.kW_SYNCHRONIZED != null) {
            } else if (attribute.kW_ABSTRACT != null) {
            } else if (attribute.kW_AUTO != null) {
            } else if (attribute.kW_ENUM != null) {
            } else if (attribute.kW_EXTERN != null) {
            } else if (attribute.kW_FINAL != null) {
            } else if (attribute.kW_INOUT != null) {
            } else if (attribute.kW_NOTHROW != null) {
                if (isNothrow == null) {
                    isNothrow = true
                }
            } else if (attribute.kW_OVERRIDE != null) {
            } else if (attribute.kW_PURE != null) {
                if (isPure == null) {
                    isPure = true
                }
            } else if (attribute.kW_REF != null) {
            } else if (attribute.kW___GSHARED != null) {
            } else if (attribute.kW_SCOPE != null) {
            } else if (attribute.kW_STATIC != null) {
                if (isStatic == null) {
                    isStatic = true
                }
            } else if (attribute.alignAttribute != null) {
            } else if (attribute.atAttribute?.identifier?.name == "property") {
                if (isProperty == null) {
                    isProperty = true
                }
            } else if (attribute.linkageAttribute != null) {
            } else if (attribute.kW_CONST != null) {
                isConst = true
            }
        }
    }

    fun handleConstructor() {
        val constructor = startingPoint as Constructor
        //a member of a class so not static:
        defaultsToStatic = false
        //publicly acsessible by defualt
        defualtVisibility = Visibility.PUBLIC
        //a poperty constructor sounds really weird
        defaultsToProperty = false
        //I believe this is false
        defaultsToNoGC = false
        //Not extern by default I believe
        // todo need to distinguish between extern(C) and other kinds of extern
        defaultsToExtern = false
        //not really applicable
        defaultsToLocal = false
        //constructors are like the exact opposite of a pure function
        defaultsToPure = false
        //I'm pretty sure constructors are not nothrow by default
        defaultsToNothrow = false
        // what does it mean for a constructor to be const. I'm betting that since constructors can't be overridden they are const?
        defaultsToConst = true
        //it doesn't make sense for  either:
        defaultsToImmutable = false
        val decl = constructor.parent as Declaration
        for (attribute in decl.attributes) {
            if (attribute.kW_CONST != null) {
                isConst = true
            }
            if (attribute.kW_EXTERN != null) {
                isExtern = true
            }
            if (attribute.kW_NOTHROW != null) {
                isNothrow = true
            }
            if (attribute.kW_PURE != null) {
                isPure = true
            }
            if (attribute.kW_PRIVATE != null) {
                visibility = Visibility.PRIVATE
            }
            if (attribute.kW_PROTECTED != null) {
                visibility = Visibility.PROTECTED
            }
            if (attribute.kW_PUBLIC != null) {
                visibility = Visibility.PUBLIC
            }
//            if (attribute.kW_FINAL != null) {
//
//            }
            if (attribute.kW_STATIC != null) {
                isStatic = true
            }
        }

    }

    fun handleFunctionDeclaration() {

    }

    fun handleInterfaceOrClass() {

    }

    fun handleUnionDeclaration() {

    }

    fun handleStructDeclaration() {

    }

    fun handleLabeledStatement() {

    }

    fun handleAutoDeclarationPart() {

    }

    fun handleEnumDeclaration() {

    }

    fun handleCatch() {

    }

    fun handleDeclarator() {

    }

    fun handleEponymousTemplateDeclaration() {

    }

    fun handleForeachType() {

    }

    fun handleDLanguageIfCondition() {

    }

    fun handleTemplateDeclaration() {

    }


}
