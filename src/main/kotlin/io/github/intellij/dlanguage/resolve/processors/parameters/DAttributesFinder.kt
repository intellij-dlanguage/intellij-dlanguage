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
            defualts = handleConstructor(startingPoint)
        } else if (startingPoint is FunctionDeclaration) {
            defualts = handleFunctionDeclaration(startingPoint)
        } else if (startingPoint is InterfaceOrClass) {
            defualts = handleInterfaceOrClass(startingPoint)
        } else if (startingPoint is UnionDeclaration) {
            defualts = handleUnionDeclaration(startingPoint)
        } else if (startingPoint is StructDeclaration) {
            defualts = handleStructDeclaration(startingPoint)
        } else if (startingPoint is LabeledStatement) {
            defualts = handleLabeledStatement(startingPoint)
        } else if (startingPoint is AutoDeclarationPart) {
            defualts = handleAutoDeclarationPart(startingPoint)
        } else if (startingPoint is EnumDeclaration) {
            defualts = handleEnumDeclaration(startingPoint)
        } else if (startingPoint is Catch) {
            defualts = handleCatch(startingPoint)
        } else if (startingPoint is Declarator) {
            defualts = handleDeclarator(startingPoint)
        } else if (startingPoint is EponymousTemplateDeclaration) {
            defualts = handleEponymousTemplateDeclaration(startingPoint)
        } else if (startingPoint is ForeachType) {
            defualts = handleForeachType(startingPoint)
        } else if (startingPoint is DLanguageIfCondition) {
            defualts = handleDLanguageIfCondition(startingPoint)
        } else if (startingPoint is TemplateDeclaration) {
            defualts = handleTemplateDeclaration(startingPoint)
        } else {
            throw IllegalArgumentException("bad type sent to AttributesFinder")
        }

    }

    enum class Visibility {
        PUBLIC, PRIVATE, PROTECTED, LOCAL
    }

    class DirectApplication (
        val static: Boolean? = null,
        val visibility: Visibility? = null,
        val property: Boolean? = null,
        val noGC: Boolean? = null,
        val extern: Boolean? = null,
        val pure: Boolean? = null,
        val local: Boolean? = null,
        val nothrow: Boolean? = null,
        val const: Boolean? = null,
        val immutable: Boolean? = null) {

    }

    val directApplication : DirectApplication
    val bulkAttributeApplied : BulkAttributeApplication

    class BulkAttributeApplication(
        val static: Boolean? = null,
        val visibility: Visibility? = null,
        val property: Boolean? = null,
        val noGC: Boolean? = null,
        val extern: Boolean? = null,
        val pure: Boolean? = null,
        val local: Boolean? = null,
        val nothrow: Boolean? = null,
        val const: Boolean? = null,
        val immutable: Boolean? = null) {

    }

    val defualts: DefaultAttributes;

    class DefaultAttributes(
        val static: Boolean = true,
        val visibility: Visibility = Visibility.PUBLIC,
        val property: Boolean = false,
        val noGC: Boolean = false,
        val extern: Boolean = false,
        val pure: Boolean = false,
        val local: Boolean = false,
        val nothrow: Boolean = false,
        val const: Boolean = false,
        val immutable: Boolean = false) {

    }


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


    //at some later date maybe make these membewrs of there respective functions to make things more object -oriented
    fun handleConstructor(constructor: Constructor): DefaultAttributes {
        val decl = constructor.parent as Declaration
        updateFromParentDecl(decl)
        return DefaultAttributes(
            static = false,
            visibility = Visibility.PUBLIC,
            const = true,
            pure = false,
            extern = false,
            noGC = false,
            immutable = false,
            nothrow = false,
            property = false
        )

    }

    fun handleFunctionDeclaration(function: FunctionDeclaration): DefaultAttributes {
        val decl = function.parent as Declaration
        updateFromParentDecl(decl)
        return DefaultAttributes(
            //functions are static by default
            static = true,
            //publicly accessible by default
            visibility = Visibility.PUBLIC,
            //functions that are properties by default sounds kind of like haskell in the sense that there would be an uncomfortable lack of parentheses
            property = false,
            //I believe this is false
            noGC = false,
            //Not extern by default I believe
            // todo need to distinguish between extern(C) and other kinds of extern
            extern = false,
            //not really applicable
            local = false,
            //functions are not pure by default
            pure = false,
            //I'm pretty sure functions are not nothrow by default
            nothrow = false,
            // functions can be overriden by default
            const = false,
            //it doesn't make sense for functions to be immutable either:
            immutable = false
        )

    }

    private fun updateFromParentDecl(decl: Declaration) {
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

    fun handleInterfaceOrClass(interfaceOrClass: InterfaceOrClass): DefaultAttributes {
        val decl = interfaceOrClass.parent.parent as Declaration
        updateFromParentDecl(decl)
        return DefaultAttributes(
            static = true,
            visibility = Visibility.PUBLIC,
            property = false,
            noGC = false,
            //Not extern by default I believe
            extern = false,
            pure = false,
            nothrow = false,
            const = false,
            immutable = false
        )
    }

    fun handleUnionDeclaration(union: UnionDeclaration): DefaultAttributes {
        val decl = union.parent as Declaration
        updateFromParentDecl(decl)
        return DefaultAttributes(
            static = true,
            visibility = Visibility.PUBLIC,
            property = false,
            noGC = false,
            //Not extern by default I believe todo check this
            extern = false,
            pure = false,
            nothrow = false,
            const = false,
            immutable = false
        )
    }

    fun handleStructDeclaration(struct: StructDeclaration): DefaultAttributes {
        val decl = struct.parent as Declaration
        updateFromParentDecl(decl)
        return DefaultAttributes(
            static = true,
            visibility = Visibility.PUBLIC,
            property = false,
            noGC = false,
            //Not extern by default I believe todo check this
            extern = false,
            pure = false,
            nothrow = false,
            const = false,
            immutable = false
        )
    }

    fun handleLabeledStatement(label: LabeledStatement): DefaultAttributes {
        return DefaultAttributes(
            static = true,
            visibility = Visibility.PUBLIC,
            property = false,
            noGC = false,
            //Not extern by default I believe todo check this
            extern = false,
            pure = false,
            nothrow = false,
            const = false,
            local = true,//the one time this is sorta the case
            immutable = false
        )
    }

    fun handleAutoDeclarationPart(autoDeclPart: AutoDeclarationPart): DefaultAttributes {
        val autoDecl = autoDeclPart.parent as AutoDeclaration
        for (storageClasss in autoDecl.storageClasss) {
            updateFromStorageClass(storageClasss)
        }
        val varDecl = autoDecl.parent as VariableDeclaration
        for (storageClasss in varDecl.storageClasss) {
            updateFromStorageClass(storageClasss)
        }
        val decl = varDecl.parent as Declaration
        updateFromParentDecl(decl)
        return DefaultAttributes(
            static = true,
            visibility = Visibility.PUBLIC,
            property = false,
            noGC = false,
            //Not extern by default I believe todo check this
            extern = false,
            pure = false,
            nothrow = false,
            const = false,
            immutable = false
        )
    }

    private fun updateFromStorageClass(storageClasss: DLanguageStorageClass) {
        if (storageClasss.kW_CONST != null) {
            isConst = true
        }
        if (storageClasss.kW_EXTERN != null) {
            isExtern = true
        }
        if (storageClasss.kW_NOTHROW != null) {
            isNothrow = true
        }
        if (storageClasss.kW_PURE != null) {
            isPure = true
        }
        if (storageClasss.kW_STATIC != null) {
            isStatic = true
        }
    }

    fun handleEnumDeclaration(enumDecl: EnumDeclaration): DefaultAttributes {
        updateFromParentDecl(enumDecl.parent as Declaration)
        return DefaultAttributes(
            static = true,
            visibility = Visibility.PUBLIC,
            property = false,
            noGC = false,
            //Not extern by default I believe todo check this
            extern = false,
            const = false,
            immutable = false)
    }

    fun handleCatch(catch: Catch): DefaultAttributes {
        return DefaultAttributes(
            static = true,
            local = true,
            visibility = Visibility.PUBLIC,
            //Not extern by default I believe todo check this
            extern = false,
            const = false,//todo check this,
            immutable = false)
        //cannot be const/immutable etc, so no need to update from attributes
    }

    fun handleDeclarator(decl: Declarator): DefaultAttributes {
        val varDecls = decl.parent as VariableDeclaration
        for (storageClasss in varDecls.storageClasss) {
            updateFromStorageClass(storageClasss)
        }
        return DefaultAttributes(
            static = true,
            visibility = Visibility.PUBLIC,
            property = false,
            noGC = false,
            //Not extern by default I believe todo check this
            extern = false,
            pure = false,
            nothrow = false,
            const = false,
            immutable = false
        )
    }

    fun handleEponymousTemplateDeclaration(decl: EponymousTemplateDeclaration): DefaultAttributes {
        updateFromParentDecl(decl.parent as Declaration)
        return DefaultAttributes(
            static = true,
            visibility = Visibility.PUBLIC,
            property = false,
            noGC = false,
            //Not extern by default I believe todo check this
            extern = false,
            pure = false,
            nothrow = false,
            const = false,
            immutable = false)
    }

    fun handleForeachType(): DefaultAttributes {
        //todo get attributes
        return DefaultAttributes(local = true,static = true)
    }

    fun handleDLanguageIfCondition(): DefaultAttributes {
        //todo get attributes
        return DefaultAttributes(local = true)
    }

    fun handleTemplateDeclaration(): DefaultAttributes {
        //todo get attribues
        return DefaultAttributes()
    }


}
