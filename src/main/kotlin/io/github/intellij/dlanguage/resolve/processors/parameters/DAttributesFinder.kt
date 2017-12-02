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
            defualts = defaultConstructor(startingPoint)
            directApplication = handleConstructor(startingPoint)
        } else if (startingPoint is FunctionDeclaration) {
            defualts = defaultFunctionDeclaration(startingPoint)
            directApplication = handleFunctionDeclaration(startingPoint)
        } else if (startingPoint is InterfaceOrClass) {
            defualts = defaultInterfaceOrClass(startingPoint)
            directApplication = handleInterfaceOrClass(startingPoint)
        } else if (startingPoint is UnionDeclaration) {
            defualts = defaultUnionDeclaration(startingPoint)
            directApplication = handleUnionDeclaration(startingPoint)
        } else if (startingPoint is StructDeclaration) {
            defualts = defaultStructDeclaration(startingPoint)
            directApplication = handleStructDeclaration(startingPoint)
        } else if (startingPoint is LabeledStatement) {
            defualts = defaultLabeledStatement(startingPoint)
            directApplication = handleLabeledStatement(startingPoint)
        } else if (startingPoint is AutoDeclarationPart) {
            defualts = defaultAutoDeclarationPart(startingPoint)
            directApplication = handleAutoDeclarationPart(startingPoint)
        } else if (startingPoint is EnumDeclaration) {
            defualts = defaultEnumDeclaration(startingPoint)
            directApplication = handleEnumDeclaration(startingPoint)
        } else if (startingPoint is Catch) {
            defualts = defaultCatch(startingPoint)
            directApplication = handleCatch(startingPoint)
        } else if (startingPoint is Declarator) {
            defualts = defaultDeclarator(startingPoint)
            directApplication = handleDeclarator(startingPoint)
        } else if (startingPoint is EponymousTemplateDeclaration) {
            defualts = defaultEponymousTemplateDeclaration(startingPoint)
            directApplication = handleEponymousTemplateDeclaration(startingPoint)
        } else if (startingPoint is ForeachType) {
            defualts = defaultForeachType(startingPoint)
            directApplication = handleForeachType(startingPoint)
        } else if (startingPoint is DLanguageIfCondition) {
            defualts = defaultIfCondition(startingPoint)
            directApplication = handleIfCondition(startingPoint)
        } else if (startingPoint is TemplateDeclaration) {
            defualts = defaultTemplateDeclaration(startingPoint)
            directApplication = handleTemplateDeclaration(startingPoint)
        } else {
            throw IllegalArgumentException("bad type sent to AttributesFinder")
        }

    }

    enum class Visibility {
        PUBLIC, PRIVATE, PROTECTED, LOCAL
    }

    val directApplication : DirectApplication

    class DirectApplication (
        var static: Boolean? = null,
        var visibility: Visibility? = null,
        var property: Boolean? = null,
        var noGC: Boolean? = null,
        var extern: Boolean? = null,
        var pure: Boolean? = null,
        var local: Boolean? = null,
        var nothrow: Boolean? = null,
        var const: Boolean? = null,
        var immutable: Boolean? = null) {

    }
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


    fun handleConstructor(constructor: Constructor): DirectApplication {
        val decl = constructor.parent as Declaration
        return updateFromParentDecl(decl)
    }

    //at some later date maybe make these membewrs of there respective functions to make things more object -oriented
    fun defaultConstructor(constructor: Constructor): DefaultAttributes {
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

    fun handleFunctionDeclaration(function: FunctionDeclaration): DirectApplication {
        val decl = function.parent as Declaration
        return updateFromParentDecl(decl)
    }

    fun defaultFunctionDeclaration(function: FunctionDeclaration): DefaultAttributes {
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

    private fun updateFromParentDecl(decl: Declaration) : DirectApplication{
        val attribs = DirectApplication()
        for (attribute in decl.attributes) {
            if (attribute.kW_CONST != null) {
                attribs.const = true
            }
            if (attribute.kW_EXTERN != null) {
                attribs.extern = true
            }
            if (attribute.kW_NOTHROW != null) {
                attribs.nothrow = true
            }
            if (attribute.kW_PURE != null) {
                attribs.pure = true
            }
            if (attribute.kW_PRIVATE != null) {
                attribs.visibility = Visibility.PRIVATE
            }
            if (attribute.kW_PROTECTED != null) {
                attribs.visibility = Visibility.PROTECTED
            }
            if (attribute.kW_PUBLIC != null) {
                attribs.visibility = Visibility.PUBLIC
            }
            if (attribute.kW_STATIC != null) {
                attribs.static = true
            }
        }
        return attribs
    }

    fun handleInterfaceOrClass(interfaceOrClass: InterfaceOrClass):DirectApplication{
        val decl = interfaceOrClass.parent.parent as Declaration
        return updateFromParentDecl(decl)
    }

    fun defaultInterfaceOrClass(interfaceOrClass: InterfaceOrClass): DefaultAttributes {
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

    fun handleUnionDeclaration(union: UnionDeclaration) : DirectApplication {
        val decl = union.parent as Declaration
        return updateFromParentDecl(decl)
    }

    fun defaultUnionDeclaration(union: UnionDeclaration): DefaultAttributes {
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

    fun handleStructDeclaration(struct: StructDeclaration) : DirectApplication {
        val decl = struct.parent as Declaration
        return updateFromParentDecl(decl)
    }

    fun defaultStructDeclaration(struct: StructDeclaration): DefaultAttributes {
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

    fun handleLabeledStatement(label: LabeledStatement) : DirectApplication {
        return DirectApplication()
    }

    fun defaultLabeledStatement(label: LabeledStatement): DefaultAttributes {
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

    fun handleAutoDeclarationPart(autoDeclPart: AutoDeclarationPart) : DirectApplication {
        val autoDecl = autoDeclPart.parent as AutoDeclaration
        val varDecl = autoDecl.parent as VariableDeclaration
        val decl = varDecl.parent as Declaration
        val attribs = updateFromParentDecl(decl)
        for (storageClasss in autoDecl.storageClasss) {
            updateFromStorageClass(storageClasss,attribs)
        }
        for (storageClasss in varDecl.storageClasss) {
            updateFromStorageClass(storageClasss,attribs)
        }
        return attribs
    }

    fun defaultAutoDeclarationPart(autoDeclPart: AutoDeclarationPart): DefaultAttributes {
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

    private fun updateFromStorageClass(storageClasss: DLanguageStorageClass, attribs: DirectApplication) {
        if (storageClasss.kW_CONST != null) {
            attribs.const = true
        }
        if (storageClasss.kW_EXTERN != null) {
            attribs.extern = true
        }
        if (storageClasss.kW_NOTHROW != null) {
            attribs.nothrow = true
        }
        if (storageClasss.kW_PURE != null) {
            attribs.pure = true
        }
        if (storageClasss.kW_STATIC != null) {
            attribs.static = true
        }
    }

    fun handleEnumDeclaration(enumDecl: EnumDeclaration) : DirectApplication {
        return updateFromParentDecl(enumDecl.parent as Declaration)
    }

    fun defaultEnumDeclaration(enumDecl: EnumDeclaration): DefaultAttributes {
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

    fun handleCatch(catch: Catch) : DirectApplication {
        return DirectApplication()
    }

    fun defaultCatch(catch: Catch): DefaultAttributes {
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

    fun handleDeclarator(decl: Declarator) : DirectApplication {
        val varDecls = decl.parent as VariableDeclaration
        val attribs = DirectApplication()
        for (storageClasss in varDecls.storageClasss) {
            updateFromStorageClass(storageClasss, attribs)
        }
        return attribs
    }

    fun defaultDeclarator(decl: Declarator): DefaultAttributes {
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

    fun handleEponymousTemplateDeclaration(decl: EponymousTemplateDeclaration) : DirectApplication {
        return updateFromParentDecl(decl.parent as Declaration)
    }

    fun defaultEponymousTemplateDeclaration(decl: EponymousTemplateDeclaration): DefaultAttributes {
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

    fun handleForeachType(foreachType: DLanguageForeachType): DirectApplication {
        return DirectApplication()
    }

    fun defaultForeachType(foreachType: DLanguageForeachType): DefaultAttributes {
        return DefaultAttributes(local = true,static = true)
    }

    fun handleIfCondition(ifCondition: DLanguageIfCondition) : DirectApplication {
        return DirectApplication()
    }

    fun defaultIfCondition(ifCondition: DLanguageIfCondition): DefaultAttributes {
        //todo get attributes
        return DefaultAttributes(local = true)
    }

    fun handleTemplateDeclaration(templateDecl: DlangTemplateDeclaration): DirectApplication {
        return DirectApplication()
    }

    fun defaultTemplateDeclaration(templateDecl: DlangTemplateDeclaration): DefaultAttributes {
        //todo get attribues
        return DefaultAttributes()
    }


}
