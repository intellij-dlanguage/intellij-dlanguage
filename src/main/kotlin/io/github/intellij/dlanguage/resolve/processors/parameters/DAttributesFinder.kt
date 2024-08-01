@file:Suppress("UNUSED_PARAMETER")

package io.github.intellij.dlanguage.resolve.processors.parameters

import com.intellij.openapi.util.Condition
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.psi.*
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.psi.interfaces.Declaration
import io.github.intellij.dlanguage.psi.named.DlangSingleImport
import io.github.intellij.dlanguage.psi.named.DlangTemplateDeclaration
import io.github.intellij.dlanguage.utils.*
import java.io.IOException

@Suppress("UNUSED_PARAMETER")
/**
 * Created by francis on 7/17/2017.
 */
class DAttributesFinder {

    val startingPoint: PsiElement

    constructor(startingPoint: PsiElement) {
        this.startingPoint = startingPoint

        val elem: DNamedElement
        if (startingPoint !is DNamedElement) {
            val parentNamedElement = PsiTreeUtil.findFirstParent(startingPoint, { it is DNamedElement })
            if (parentNamedElement == null) {
                throw IllegalArgumentException("Asked for attributes of something that isn't a DNamedElement, or the child of a DNamedElement")
            }
            elem = parentNamedElement as DNamedElement
        } else {
            elem = startingPoint
        }
        if (elem is Constructor) {
            defualts = defaultConstructor(elem)
            directApplication = handleConstructor(elem)
        } else if (elem is FunctionDeclaration) {
            defualts = defaultFunctionDeclaration(elem)
            directApplication = handleFunctionDeclaration(elem)
        } else if (elem is ClassDeclaration) {
            defualts = defaultClassDeclaration(elem)
            directApplication = handleClassDeclaration(elem)
        } else if (elem is InterfaceDeclaration) {
            defualts = defaultInterfaceDeclaration(elem)
            directApplication = handleInterfaceDeclaration(elem)
        } else if (elem is UnionDeclaration) {
            defualts = defaultUnionDeclaration(elem)
            directApplication = handleUnionDeclaration(elem)
        } else if (elem is StructDeclaration) {
            defualts = defaultStructDeclaration(elem)
            directApplication = handleStructDeclaration(elem)
        } else if (elem is LabeledStatement) {
            defualts = defaultLabeledStatement(elem)
            directApplication = handleLabeledStatement(elem)
        } else if (elem is AutoAssignment) {
            defualts = defaultAutoDeclarationPart(elem)
            directApplication = handleAutoDeclarationPart(elem)
        } else if (elem is EnumDeclaration) {
            defualts = defaultEnumDeclaration(elem)
            directApplication = handleEnumDeclaration(elem)
        } else if (elem is Catch) {
            defualts = defaultCatch(elem)
            directApplication = handleCatch(elem)
        } else if (elem is IdentifierInitializer) {
            defualts = defaultIdentifierInitializer(elem)
            directApplication = handleIdentifierInitializer(elem)
        } else if (elem is ForeachType) {
            defualts = defaultForeachType(elem)
            directApplication = handleForeachType(elem)
        } else if (elem is DLanguageIfCondition) {
            defualts = defaultIfCondition(elem)
            directApplication = handleIfCondition(elem)
        } else if (elem is TemplateDeclaration) {
            defualts = defaultTemplateDeclaration(elem)
            directApplication = handleTemplateDeclaration(elem)
        } else if (elem is SingleImport) {
            defualts = defaultSingleImport(elem)
            directApplication = handleSingleImport(elem)
        } else if (elem is Parameter) {
            defualts = defaultParameter(elem)
            directApplication = handleParameter(elem)
        } else if (elem is ModuleDeclaration) {
            defualts = defaultModuleDecl(elem)
            directApplication = handleModuleDecl(elem)
        } else if (elem is AliasInitializer) {
            defualts = defaultAliasInit(elem)
            directApplication = handleAliasInit(elem)
        } else if (elem is EnumMember) {
            defualts = defaultEnumMember(elem)
            directApplication = handleEnumMember(elem)
        } else if (elem is DLanguageNamedImportBind) {
            defualts = defaultNamedImportBind(elem)
            directApplication = handleNamedImportBind(elem)
        } else if (elem is VersionSpecification) {
            defualts = defaultVersionSpecifcation(elem)
            directApplication = handleVersionSpecification(elem)
        } else {
            throw IllegalArgumentException("bad type sent to AttributesFinder")
        }
        recurseUp()

    }

    private fun handleVersionSpecification(elem: VersionSpecification): DirectApplication {
        return DirectApplication(visibility = Visibility.PUBLIC)
    }

    private fun defaultVersionSpecifcation(elem: VersionSpecification): DefaultAttributes {
        return DefaultAttributes()
    }

    private fun handleNamedImportBind(elem: DLanguageNamedImportBind): DirectApplication {
        return DirectApplication()//perhaps check if private/public on mport also applies to this.
    }

    private fun defaultNamedImportBind(elem: DLanguageNamedImportBind): DefaultAttributes {
        return DefaultAttributes()
    }

    private fun handleEnumMember(elem: EnumMember): DirectApplication {
        return DirectApplication()
    }

    private fun defaultEnumMember(elem: EnumMember): DefaultAttributes {
        return DefaultAttributes(static = false, visibility = Visibility.PUBLIC, const = true, immutable = true)
    }

    private fun handleAliasInit(aliasInit: AliasInitializer): DirectApplication {
        val aliasDecl = aliasInit.parent as AliasDeclaration
        val attribs = updateFromParentDecl(aliasDecl)
        for (storageClasss in aliasInit.storageClasss) {
            updateFromStorageClass(storageClasss, attribs)
        }
        for (storageClasss in aliasDecl.storageClasss) {
            updateFromStorageClass(storageClasss, attribs)
        }
        return attribs
    }

    private fun defaultAliasInit(startingPoint: AliasInitializer): DefaultAttributes {
        return DefaultAttributes()
    }

    private fun handleModuleDecl(startingPoint: ModuleDeclaration): DirectApplication {
        return DirectApplication()
    }

    private fun defaultModuleDecl(startingPoint: ModuleDeclaration): DefaultAttributes {
        return DefaultAttributes()
    }

    private fun defaultParameter(parameter: Parameter): DefaultAttributes {
        return DefaultAttributes(visibility = Visibility.LOCAL)
    }

    private fun handleParameter(param: Parameter): DirectApplication {
        val attributes = DirectApplication()
        for (attribute in param.parameterAttributes) {
//            attribute.kW_AUTO
//            attribute.kW_FINAL
//            attribute.kW_IN
//            attribute.kW_LAZY
//            attribute.kW_OUT
//            attribute.kW_REF
//            attribute.kW_SCOPE
        }
        return attributes
    }

    private fun handleSingleImport(startingPoint: SingleImport): DirectApplication {
        val decl: Declaration
        if (startingPoint.parent is ImportDeclaration) {
            decl = startingPoint.parent as ImportDeclaration
        } else {
            val bind = startingPoint.parent as ImportBindings
            decl = bind.parent as ImportDeclaration
        }
        return updateFromParentDecl(decl)
    }

    fun defaultSingleImport(startingPoint: SingleImport): DefaultAttributes {
        return DefaultAttributes(visibility = Visibility.PRIVATE, static = true)
    }

    enum class Visibility {
        PUBLIC, PRIVATE, PROTECTED, LOCAL;

        fun write(stream: StubOutputStream) {
            when (this) {
                DAttributesFinder.Visibility.PUBLIC -> stream.writeInt(1)
                DAttributesFinder.Visibility.PRIVATE -> stream.writeInt(2)
                DAttributesFinder.Visibility.PROTECTED -> stream.writeInt(3)
                DAttributesFinder.Visibility.LOCAL -> stream.writeInt(4)
            }
        }

        companion object {
            fun read(stream: StubInputStream): Visibility {
                when (stream.readInt()) {
                    1 -> return PUBLIC
                    2 -> return PRIVATE
                    3 -> return PROTECTED
                    4 -> return LOCAL
                }
                throw IOException("read illegal int when deserializing Visibility")
            }
        }
    }

    val directApplication: DirectApplication

    class DirectApplication(
        var static: Boolean? = null,
        var visibility: Visibility? = null,
        var property: Boolean? = null,
        var noGC: Boolean? = null,
        var extern: Boolean? = null,
        var pure: Boolean? = null,
        var nothrow: Boolean? = null,
        var const: Boolean? = null,
        var immutable: Boolean? = null,
        var enum: Boolean? = null)//enum refers to compile time not enum type

    val bulkAttributeApplied: BulkAttributeApplication = BulkAttributeApplication()

    class BulkAttributeApplication(
        var static: Boolean? = null,
        var visibility: Visibility? = null,
        var property: Boolean? = null,
        var noGC: Boolean? = null,
        var extern: Boolean? = null,
        var pure: Boolean? = null,
        var nothrow: Boolean? = null,
        var const: Boolean? = null,
        var immutable: Boolean? = null
        /*var enum: Boolean? = null*/)

    val defualts: DefaultAttributes

    class DefaultAttributes(
        val static: Boolean = true,
        val visibility: Visibility = Visibility.PUBLIC,
        val property: Boolean = false,
        val noGC: Boolean = false,
        val extern: Boolean = false,
        val pure: Boolean = false,
        val nothrow: Boolean = false,
        val const: Boolean = false,
        val immutable: Boolean = false,
        val enum: Boolean = false)


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
                if (point is DlangPsiFile || point.prevSibling == null) {
                    break
                }
                point = point.prevSibling
            }
            if (point.parent == null || point is PsiFile || point is PsiDirectory) {
                break
            }
            point = point.parent
        }
    }

    fun isParent(parent: PsiElement, child: PsiElement): Boolean {
        if (child == parent)
            return true
        if (child is DlangPsiFile) {
            return false
        }
        return isParent(parent, child.parent)
    }

    fun execute(element: PsiElement): Boolean {
        if (element is DlangSingleImport && isParent(element, startingPoint)) {
            bulkAttributeApplied.static = false
        }
        if (element is FunctionDeclaration || element is DLanguageUnittest || element is Parameters || element is TemplateParameters) {
            if (element is FunctionDeclaration) {
                if ((element.functionBody != null && isParent(element.functionBody!!, startingPoint))
                    || (element.parameters != null && isParent(element.parameters!!, startingPoint))
                    || (element.templateParameters != null && isParent(element.templateParameters!!, startingPoint))) {
                    if (bulkAttributeApplied.visibility == null) {
                        bulkAttributeApplied.visibility = Visibility.LOCAL
                    }
                    return false
                }
            }
            return true
        }
        if (element is StructBody) {
            if (isParent(element, startingPoint)) {
                bulkAttributeApplied.static = false
                return false
            }
        }
        if (element is Attribute) {
            updateFromAttribute(element)
        }
        if (element is AttributeSpecifier) {
            updateFromAttribute(element.attribute!!)
        }
        return true
    }

    fun updateFromAttribute(attribute: DLanguageAttribute) {
        if (attribute.textOffset < startingPoint.textOffset) {

            if (attribute.kW_EXPORT != null) {
            } else if (attribute.kW_PACKAGE != null) {
            } else if (attribute.kW_PRIVATE != null) {
                if (bulkAttributeApplied.visibility == null) {
                    bulkAttributeApplied.visibility = Visibility.PRIVATE
                }
            } else if (attribute.kW_PROTECTED != null) {
                if (bulkAttributeApplied.visibility == null) {
                    bulkAttributeApplied.visibility = Visibility.PROTECTED
                }
            } else if (attribute.kW_PUBLIC != null) {
                if (bulkAttributeApplied.visibility == null) {
                    bulkAttributeApplied.visibility = Visibility.PUBLIC
                }
            } else if (attribute.pragmaExpression != null) {
            } else if (attribute.kW_SYNCHRONIZED != null) {
            } else if (attribute.kW_ABSTRACT != null) {
            } else if (attribute.kW_AUTO != null) {
            } else if (attribute.kW_ENUM != null) {
                /*if(bulkAttributeApplied.enum == null){
                    bulkAttributeApplied.enum = true
                }*/
            } else if (attribute.kW_EXTERN != null) {
            } else if (attribute.kW_FINAL != null) {
            } else if (attribute.kW_INOUT != null) {
            } else if (attribute.kW_NOTHROW != null) {
                if (bulkAttributeApplied.nothrow == null) {
                    bulkAttributeApplied.nothrow = true
                }
            } else if (attribute.kW_OVERRIDE != null) {
            } else if (attribute.kW_PURE != null) {
                if (bulkAttributeApplied.pure == null) {
                    bulkAttributeApplied.pure = true
                }
            } else if (attribute.kW_REF != null) {
            } else if (attribute.kW___GSHARED != null) {
            } else if (attribute.kW_SCOPE != null) {
            } else if (attribute.kW_STATIC != null) {
                if (bulkAttributeApplied.static == null) {
                    bulkAttributeApplied.static = true
                }
            } else if (attribute.alignAttribute != null) {
            } else if (attribute.atAttribute?.identifier?.text == "property") {
                if (bulkAttributeApplied.property == null) {
                    bulkAttributeApplied.property = true
                }
            } else if (attribute.linkageAttribute != null) {
            } else if (attribute.kW_CONST != null) {
                bulkAttributeApplied.const = true
            }
        }
    }


    fun handleConstructor(constructor: Constructor): DirectApplication {
        return updateFromParentDecl(constructor)
    }

    //todo at some later date maybe make these members of there respective functions to make things more object -oriented
    @Suppress("UNUSED_PARAMETER")
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
        val attribs = updateFromParentDecl(function)
        handle(function.memberFunctionAttributes, attribs)
        return attribs
    }

    private fun handle(memberFunctionAttributes: List<DLanguageMemberFunctionAttribute>, attribs: DirectApplication) {
        for (memberFunctionAttribute in memberFunctionAttributes) {
            if (memberFunctionAttribute.kW_CONST != null) {
                attribs.const = true
            }
            if (memberFunctionAttribute.kW_IMMUTABLE != null) {
                attribs.immutable = true
            }
        }
    }

    @Suppress("UNUSED_PARAMETER")
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

    private fun updateFromParentDecl(decl: Declaration): DirectApplication {
        val attribs = DirectApplication()
        /*for (attribute in decl.attributes) {
            if (attribute.kW_ENUM != null) {
                attribs.enum = true
            }
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
        }*/
        return attribs
    }

    fun handleClassDeclaration(classDeclaration: ClassDeclaration): DirectApplication {
        return updateFromParentDecl(classDeclaration)
    }

    fun handleInterfaceDeclaration(interfaceDeclaration: InterfaceDeclaration): DirectApplication {
        return updateFromParentDecl(interfaceDeclaration)
    }

    fun defaultClassDeclaration(classDeclaration: ClassDeclaration): DefaultAttributes {
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

    fun defaultInterfaceDeclaration(interfaceDeclaration: InterfaceDeclaration): DefaultAttributes {
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

    fun handleUnionDeclaration(union: UnionDeclaration): DirectApplication {
        return updateFromParentDecl(union)
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

    fun handleStructDeclaration(struct: StructDeclaration): DirectApplication {
        return updateFromParentDecl(struct)
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

    @Suppress("UNUSED_PARAMETER")
    fun handleLabeledStatement(label: LabeledStatement): DirectApplication {
        return DirectApplication()
    }

    @Suppress("UNUSED_PARAMETER")
    fun defaultLabeledStatement(label: LabeledStatement): DefaultAttributes {
        return DefaultAttributes(
            static = true,
            visibility = Visibility.LOCAL,
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

    fun handleAutoDeclarationPart(autoDeclPart: AutoAssignment): DirectApplication {
        val autoDecl = autoDeclPart.parent as AutoDeclaration
        val attribs = updateFromParentDecl(autoDecl)
        for (storageClasss in autoDecl.storageClasss) {
            updateFromStorageClass(storageClasss, attribs)
        }
        return attribs
    }

    @Suppress("UNUSED_PARAMETER")
    fun defaultAutoDeclarationPart(autoDeclPart: AutoAssignment): DefaultAttributes {
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
        if (storageClasss.kW_ENUM != null) {
            attribs.enum = true
        }
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

    fun handleEnumDeclaration(enumDecl: EnumDeclaration): DirectApplication {
        return updateFromParentDecl(enumDecl)
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

    @Suppress("UNUSED_PARAMETER")
    fun handleCatch(catch: Catch): DirectApplication {
        return DirectApplication()
    }

    @Suppress("UNUSED_PARAMETER")
    fun defaultCatch(catch: Catch): DefaultAttributes {
        return DefaultAttributes(
            static = true,
            visibility = Visibility.LOCAL,
            //Not extern by default I believe todo check this
            extern = false,
            const = false,//todo check this,
            immutable = false)
        //cannot be const/immutable etc, so no need to update from attributes
    }

    fun handleIdentifierInitializer(decl: IdentifierInitializer): DirectApplication {
        val varDecls = decl.parent as SpecifiedVariableDeclaration
        val attribs = DirectApplication()
        for (storageClasss in varDecls.storageClasss) {
            updateFromStorageClass(storageClasss, attribs)
        }
        return attribs
    }

    fun defaultIdentifierInitializer(decl: IdentifierInitializer): DefaultAttributes {
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

    @Suppress("UNUSED_PARAMETER")
    fun handleForeachType(foreachType: ForeachType): DirectApplication {
        return DirectApplication()
    }

    @Suppress("UNUSED_PARAMETER")
    fun defaultForeachType(foreachType: ForeachType): DefaultAttributes {
        return DefaultAttributes(visibility = Visibility.LOCAL, static = true)
    }

    @Suppress("UNUSED_PARAMETER")
    fun handleIfCondition(ifCondition: DLanguageIfCondition): DirectApplication {
        return DirectApplication()
    }

    @Suppress("UNUSED_PARAMETER")
    fun defaultIfCondition(ifCondition: DLanguageIfCondition): DefaultAttributes {
        //todo get attributes
        return DefaultAttributes(visibility = Visibility.LOCAL)
    }

    @Suppress("UNUSED_PARAMETER")
    fun handleTemplateDeclaration(templateDecl: DlangTemplateDeclaration): DirectApplication {
        return DirectApplication()
    }

    @Suppress("UNUSED_PARAMETER")
    fun defaultTemplateDeclaration(templateDecl: DlangTemplateDeclaration): DefaultAttributes {
        //todo get attribues
        return DefaultAttributes()
    }

    fun isStatic(): Boolean {
        return directApplication.static ?: (bulkAttributeApplied.static ?: defualts.static)
    }

    fun isPublic() = visibility() == Visibility.PUBLIC
    fun isProtected() = visibility() == Visibility.PROTECTED
    fun isPrivate() = visibility() == Visibility.PRIVATE
    fun isLocal() = visibility() == Visibility.LOCAL
    fun visibility(): Visibility {
        return directApplication.visibility ?: (bulkAttributeApplied.visibility ?: defualts.visibility)
    }

    fun isProperty(): Boolean {
        return directApplication.property ?: (bulkAttributeApplied.property ?: defualts.property)
    }

    fun isNoGC(): Boolean {
        return directApplication.noGC ?: (bulkAttributeApplied.noGC ?: defualts.noGC)
    }

    fun isExtern(): Boolean {
        return directApplication.extern ?: (bulkAttributeApplied.extern ?: defualts.extern)
    }

    fun isPure(): Boolean {
        return directApplication.pure ?: (bulkAttributeApplied.pure ?: defualts.pure)
    }

    fun isNothrow(): Boolean {
        return directApplication.nothrow ?: (bulkAttributeApplied.nothrow ?: defualts.nothrow)
    }

    fun isConst(): Boolean {
        return directApplication.const ?: (bulkAttributeApplied.const ?: defualts.const)
    }

    fun isImmutable(): Boolean {
        return directApplication.immutable ?: (bulkAttributeApplied.immutable ?: defualts.immutable)
    }

    fun isEnum(): Boolean {
        return directApplication.enum ?: defualts.enum
    }

    val attributes: DAttributes
        get() = DAttributes(isStatic(), visibility(), isProperty(), isNoGC(), isExtern(), isPure(), isNothrow(), isConst(), isImmutable(), isEnum())


}

class DAttributes(
    val static: Boolean,
    val visibility: DAttributesFinder.Visibility,
    val property: Boolean,
    val noGC: Boolean,
    val extern: Boolean,
    val pure: Boolean,
    val nothrow: Boolean,
    val const: Boolean,
    val immutable: Boolean,
    val enum: Boolean) {
    fun write(stream: StubOutputStream) {
        stream.writeBoolean(static)
        stream.writeBoolean(property)
        stream.writeBoolean(noGC)
        stream.writeBoolean(extern)
        stream.writeBoolean(pure)
        stream.writeBoolean(nothrow)
        stream.writeBoolean(const)
        stream.writeBoolean(immutable)
        stream.writeBoolean(enum)
        visibility.write(stream)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DAttributes) return false

        if (static != other.static) return false
        if (visibility != other.visibility) return false
        if (property != other.property) return false
        if (noGC != other.noGC) return false
        if (extern != other.extern) return false
        if (pure != other.pure) return false
        if (nothrow != other.nothrow) return false
        if (const != other.const) return false
        if (immutable != other.immutable) return false
        if (enum != other.enum) return false

        return true
    }

    override fun hashCode(): Int {
        var result = static.hashCode()
        result = 31 * result + visibility.hashCode()
        result = 31 * result + property.hashCode()
        result = 31 * result + noGC.hashCode()
        result = 31 * result + extern.hashCode()
        result = 31 * result + pure.hashCode()
        result = 31 * result + nothrow.hashCode()
        result = 31 * result + const.hashCode()
        result = 31 * result + immutable.hashCode()
        result = 31 * result + enum.hashCode()
        return result
    }


    companion object {
        fun read(stream: StubInputStream): DAttributes {
            val static = stream.readBoolean()
            val property = stream.readBoolean()
            val noGC = stream.readBoolean()
            val extern = stream.readBoolean()
            val pure = stream.readBoolean()
            val nothrow = stream.readBoolean()
            val const = stream.readBoolean()
            val immutable = stream.readBoolean()
            val enum = stream.readBoolean()
            val visibility = DAttributesFinder.Visibility.read(stream)
            return DAttributes(static, visibility, property, noGC, extern, pure, nothrow, const, immutable, enum)
        }
    }


}

