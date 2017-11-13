package io.github.intellij.dlanguage.resolve.processors.parameters

import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.psi.*
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.utils.*

/**
 * Created by francis on 7/17/2017.
 */
class DAttributesFinder {

    val startingPoint: PsiElement

    constructor(startingPoint: PsiElement) {
        this.startingPoint = startingPoint
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

    var defaultsToStatic: Boolean = true
    var defaultsToPrivate: Boolean = false
    var defaultsToProtected: Boolean = false
    var defaultsToPublic: Boolean = true
    var defaultsToProperty: Boolean = false
    var defaultsToNoGC: Boolean = false
    var defaultsToExtern: Boolean = false
    var defaultsToLocal: Boolean = false
    var defaultsToPure: Boolean = false
    var defaultsToNothrow: Boolean = false
    var defaultsToConst: Boolean = false
    var defaultsToImmutable: Boolean = false


    fun recurseUp() {
        if (startingPoint is io.github.intellij.dlanguage.psi.interfaces.DNamedElement) {
            if (startingPoint is Constructor) {
                recurseUpImpl(startingPoint.kW_THIS!!)
            } else if (startingPoint is FunctionDeclaration) {
                recurseUpImpl(startingPoint.identifier!!)
            } else if (startingPoint is InterfaceOrClass) {
                recurseUpImpl(startingPoint.identifier!!)
            } else if (startingPoint is UnionDeclaration) {
                recurseUpImpl(startingPoint.identifier!!)
            } else if (startingPoint is StructDeclaration) {
                recurseUpImpl(startingPoint.identifier!!)
            } else if (startingPoint is LabeledStatement) {
                recurseUpImpl(startingPoint.identifier!!)
            } else if (startingPoint is AutoDeclarationPart) {
                recurseUpImpl(startingPoint.identifier!!)
            } else if (startingPoint is EnumDeclaration) {
                recurseUpImpl(startingPoint.identifier!!)
            } else if (startingPoint is Catch) {
                recurseUpImpl(startingPoint.identifier!!)
            } else if (startingPoint is Declarator) {
                recurseUpImpl(startingPoint.identifier!!)
            } else if (startingPoint is EponymousTemplateDeclaration) {
                recurseUpImpl(startingPoint.identifier!!)
            } else if (startingPoint is ForeachType) {
                recurseUpImpl(startingPoint.identifier!!)
            } else if (startingPoint is io.github.intellij.dlanguage.psi.DLanguageIfCondition) {
                recurseUpImpl(startingPoint.identifier!!)
            } /*else if (startingPoint is SingleImport) {
                recurseUpImpl(startingPoint.identifier!!)
            }*/ else if (startingPoint is TemplateDeclaration) {
                recurseUpImpl(startingPoint.identifier!!)
            }
        }
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
        if (child is io.github.intellij.dlanguage.psi.DlangFile)
            return false
        return isParent(parent, child.parent)
    }


    fun execute(element: PsiElement): Boolean {
        if (element is io.github.intellij.dlanguage.psi.DlangSingleImport && isParent(element, startingPoint)) {
            defaultsToPrivate = true
            defaultsToPublic = false
            defaultsToStatic = false
        }
        if (element is FunctionDeclaration || element is io.github.intellij.dlanguage.psi.DLanguageUnittest || element is Parameters || element is TemplateParameters) {
            if (element is FunctionDeclaration) {
                if ((element.functionBody != null && isParent(element.functionBody!!, startingPoint)) || (element.parameters != null && isParent(element.parameters!!, startingPoint)) || (element.templateParameters != null && isParent(element.templateParameters!!, startingPoint))) {
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


    fun updateFromAttribute(attribute: io.github.intellij.dlanguage.psi.DLanguageAttribute) {
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
            } //else if (attribute.typeConstructor != null) {
//            }
        }
    }

    fun isStatic(): Boolean {
        return isStatic ?: defaultsToStatic
    }

    fun isPrivate(): Boolean {
        if (visibility == null)
            return defaultsToPrivate
        return visibility == Visibility.PRIVATE
    }

    fun isProtected(): Boolean {
        if (visibility == null)
            return defaultsToProtected
        return visibility == Visibility.PROTECTED
    }

    fun isPublic(): Boolean {
        if (visibility == null)
            return defaultsToPublic
        return visibility == Visibility.PUBLIC
    }

    fun isProperty(): Boolean {
        return isProperty ?: defaultsToProperty
    }

    fun isNoGC(): Boolean {
        return isNoGC ?: defaultsToNoGC
    }

    fun isExtern(): Boolean {
        return isExtern ?: defaultsToExtern
    }

    fun isLocal(): Boolean {
        if (visibility == null)
            return defaultsToLocal
        return visibility == Visibility.LOCAL
    }

    fun isPure(): Boolean {
        return isPure ?: defaultsToPure
    }

    fun isNothrow(): Boolean {
        return isNothrow ?: defaultsToNothrow
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as DAttributesFinder

        if (startingPoint != other.startingPoint) return false
        if (visibility != other.visibility) return false
        if (isStatic != other.isStatic) return false
        if (isProperty != other.isProperty) return false
        if (isNoGC != other.isNoGC) return false
        if (isExtern != other.isExtern) return false
        if (isPure != other.isPure) return false
        if (isNothrow != other.isNothrow) return false
        if (isConst != other.isConst) return false
        if (isImmutable != other.isImmutable) return false
        if (defaultsToStatic != other.defaultsToStatic) return false
        if (defaultsToPrivate != other.defaultsToPrivate) return false
        if (defaultsToProtected != other.defaultsToProtected) return false
        if (defaultsToPublic != other.defaultsToPublic) return false
        if (defaultsToProperty != other.defaultsToProperty) return false
        if (defaultsToNoGC != other.defaultsToNoGC) return false
        if (defaultsToExtern != other.defaultsToExtern) return false
        if (defaultsToLocal != other.defaultsToLocal) return false
        if (defaultsToPure != other.defaultsToPure) return false
        if (defaultsToNothrow != other.defaultsToNothrow) return false
        if (defaultsToConst != other.defaultsToConst) return false
        if (defaultsToImmutable != other.defaultsToImmutable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = startingPoint.hashCode()
        result = 31 * result + (visibility?.hashCode() ?: 0)
        result = 31 * result + (isStatic?.hashCode() ?: 0)
        result = 31 * result + (isProperty?.hashCode() ?: 0)
        result = 31 * result + (isNoGC?.hashCode() ?: 0)
        result = 31 * result + (isExtern?.hashCode() ?: 0)
        result = 31 * result + (isPure?.hashCode() ?: 0)
        result = 31 * result + (isNothrow?.hashCode() ?: 0)
        result = 31 * result + (isConst?.hashCode() ?: 0)
        result = 31 * result + (isImmutable?.hashCode() ?: 0)
        result = 31 * result + defaultsToStatic.hashCode()
        result = 31 * result + defaultsToPrivate.hashCode()
        result = 31 * result + defaultsToProtected.hashCode()
        result = 31 * result + defaultsToPublic.hashCode()
        result = 31 * result + defaultsToProperty.hashCode()
        result = 31 * result + defaultsToNoGC.hashCode()
        result = 31 * result + defaultsToExtern.hashCode()
        result = 31 * result + defaultsToLocal.hashCode()
        result = 31 * result + defaultsToPure.hashCode()
        result = 31 * result + defaultsToNothrow.hashCode()
        result = 31 * result + defaultsToConst.hashCode()
        result = 31 * result + defaultsToImmutable.hashCode()
        return result
    }


}
