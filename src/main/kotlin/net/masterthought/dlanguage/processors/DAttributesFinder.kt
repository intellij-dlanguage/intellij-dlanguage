package net.masterthought.dlanguage.processors

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil.isAncestor
import net.masterthought.dlanguage.psi.DLanguageAttribute
import net.masterthought.dlanguage.psi.DLanguageSingleImport
import net.masterthought.dlanguage.psi.DLanguageUnittest
import net.masterthought.dlanguage.utils.*

/**
 * Created by francis on 7/17/2017.
 */
class DAttributesFinder {
    //todo should this be in resolve state?

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
//    private var isLocal: Boolean? = null

    var defaultsToStatic: Boolean = true
    var defaultsToPrivate: Boolean = false
    var defaultsToProtected: Boolean = false
    var defaultsToPublic: Boolean = true
    var defaultsToProperty: Boolean = false
    var defaultsToNoGC: Boolean = false
    var defaultsToExtern: Boolean = false
    var defaultsToLocal: Boolean = false

    fun recurseUp() {
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


    fun execute(element: PsiElement): Boolean {
        if (element is DLanguageSingleImport && isAncestor(startingPoint, element, true)) {
            defaultsToPrivate = true
            defaultsToPublic = false
            return false
        }
        if (element is FunctionDeclaration || element is DLanguageUnittest || element is Parameters || element is TemplateParameters) {
            if (element is FunctionDeclaration && element.functionBody != null && (isAncestor(element.functionBody!!, startingPoint, false) || isAncestor(element.parameters!!, startingPoint, false) || isAncestor(element.templateParameters!!, startingPoint, false))) {
                visibility = Visibility.LOCAL
                return false
            }
            return true
        }
        if (element is StructBody) {
            if (isAncestor(element, startingPoint, false)) {
                defaultsToStatic = false
                return false
            }
        }
        if (element is Attribute) {
            updateFromAttribute(element)
        }
        if(element is Declaration){
            if(element.attributeDeclaration != null){
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
            } else if (attribute.kW_OVERRIDE != null) {
            } else if (attribute.kW_PURE != null) {
            } else if (attribute.kW_REF != null) {
            } else if (attribute.kW___GSHARED != null) {
            } else if (attribute.kW_SCOPE != null) {
            } else if (attribute.kW_STATIC != null) {
                if (isStatic == null) {
                    isStatic = true
                }
            } else if (attribute.alignAttribute != null) {
            } else if (attribute.atAttribute != null) {
                if (attribute.atAttribute!!.identifier!!.name == "property") {
                    if (isProperty == null) {
                        isProperty = true
                    }
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
        if(visibility == null)
            return defaultsToLocal
        return visibility == Visibility.LOCAL
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
        if (defaultsToStatic != other.defaultsToStatic) return false
        if (defaultsToPrivate != other.defaultsToPrivate) return false
        if (defaultsToProtected != other.defaultsToProtected) return false
        if (defaultsToPublic != other.defaultsToPublic) return false
        if (defaultsToProperty != other.defaultsToProperty) return false
        if (defaultsToNoGC != other.defaultsToNoGC) return false
        if (defaultsToExtern != other.defaultsToExtern) return false
        if (defaultsToLocal != other.defaultsToLocal) return false

        return true
    }

    override fun hashCode(): Int {
        var result = startingPoint.hashCode()
        result = 31 * result + (visibility?.hashCode() ?: 0)
        result = 31 * result + (isStatic?.hashCode() ?: 0)
        result = 31 * result + (isProperty?.hashCode() ?: 0)
        result = 31 * result + (isNoGC?.hashCode() ?: 0)
        result = 31 * result + (isExtern?.hashCode() ?: 0)
        result = 31 * result + defaultsToStatic.hashCode()
        result = 31 * result + defaultsToPrivate.hashCode()
        result = 31 * result + defaultsToProtected.hashCode()
        result = 31 * result + defaultsToPublic.hashCode()
        result = 31 * result + defaultsToProperty.hashCode()
        result = 31 * result + defaultsToNoGC.hashCode()
        result = 31 * result + defaultsToExtern.hashCode()
        result = 31 * result + defaultsToLocal.hashCode()
        return result
    }


}
