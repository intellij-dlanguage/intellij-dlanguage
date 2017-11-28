//package io.github.intellij.dlanguage.types
//
//import io.github.intellij.dlanguage.utils.ClassDeclaration
//import io.github.intellij.dlanguage.utils.EnumDeclaration
//import io.github.intellij.dlanguage.utils.TemplateDeclaration
//
//
///**
// * Created by francis on 7/29/2017.
// */
//
//val tvoid: DType? = null
//val tint8: DType? = null
//val tuns8: DType? = null
//val tint16: DType? = null
//val tuns16: DType? = null
//val tint32: DType? = null
//val tuns32: DType? = null
//val tint64: DType? = null
//val tuns64: DType? = null
//val tint128: DType? = null
//val tuns128: DType? = null
//val tfloat32: DType? = null
//val tfloat64: DType? = null
//val tfloat80: DType? = null
//val timaginary32: DType? = null
//val timaginary64: DType? = null
//val timaginary80: DType? = null
//val tcomplex32: DType? = null
//val tcomplex64: DType? = null
//val tcomplex80: DType? = null
//val tbool: DType? = null
//val tchar: DType? = null
//val twchar: DType? = null
//val tdchar: DType? = null
//
//// Some special types
//val tshiftcnt: DType? = null
//val tvoidptr: DType? = null    // void*
//val tstring: DType? = null     // immutable(char)[]
//val twstring: DType? = null    // immutable(wchar)[]
//val tdstring: DType? = null    // immutable(dchar)[]
//val tvalist: DType? = null     // va_list alias
//val terror: DType? = null      // for error recovery
//val tnull: DType? = null       // for null type
//
//val tsize_t: DType? = null     // matches size_t alias
//val tptrdiff_t: DType? = null  // matches ptrdiff_t alias
//val thash_t: DType? = null     // matches hash_t alias
//
//
//val dtypeinfo: ClassDeclaration? = null
//val typeinfoclass: ClassDeclaration? = null
//val typeinfointerface: ClassDeclaration? = null
//val typeinfostruct: ClassDeclaration? = null
//val typeinfopointer: ClassDeclaration? = null
//val typeinfoarray: ClassDeclaration? = null
//val typeinfostaticarray: ClassDeclaration? = null
//val typeinfoassociativearray: ClassDeclaration? = null
//val typeinfovector: ClassDeclaration? = null
//val typeinfoenum: ClassDeclaration? = null
//val typeinfofunction: ClassDeclaration? = null
//val typeinfodelegate: ClassDeclaration? = null
//val typeinfotypelist: ClassDeclaration? = null
//val typeinfoconst: ClassDeclaration? = null
//val typeinfoinvariant: ClassDeclaration? = null
//val typeinfoshared: ClassDeclaration? = null
//val typeinfowild: ClassDeclaration? = null
//
//val rtinfo: TemplateDeclaration? = null
//
////DType[TMAX] basic;
////StringTable stringtable;
////todo check transative const
//
//enum class Match {
//    nomatch, // no match
//    convert, // match with conversions
//    constant, // match with conversion to const
//    exact      // exact match
//
//}
//
//
//abstract class ResolvableType {
//    abstract fun resolve(): DType
//    abstract fun actuallyIsType(): Boolean
//    //todo
//}
//
//abstract class DType {
//    //shared immutable should not be allowed
//    var MODconst: Boolean;
//    var MODimmutable: Boolean;
//    var MODshared: Boolean;
//    var MODwild: Boolean;
//    var MODwildconst: Boolean;
//    var MODmutable: Boolean;
//    var vtinfo: TypeInfoDeclaration? = null     // TypeInfo object for this DType
//    open fun implicitlyConvertibleTo(to: DType): Match {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    open fun toBaseType(): DType {
//        return this
//    }
//
//    override fun equals(other: Any?): Boolean {
//        return super.equals(other)
//    }
//
//    override fun hashCode(): Int {
//        return super.hashCode()
//    }
//
//    /************************************
//     * Apply modifiers to existing type.
//     */
//    fun castMod(MODconst: Boolean? = null, MODimmutable: Boolean? = null, MODshared: Boolean? = null, MODwild: Boolean? = null, MODwildconst: Boolean? = null, MODmutable: Boolean? = null): DType {
//        val t: DType = this
//
//        return t
//    }
//
//    /**
//     * this family of functions change the type, and do not create a copy
//     */
//    abstract fun constOf(): DType
//
//    abstract fun unSharedOf(): DType
//    abstract fun mutableOf(): DType
//    abstract fun wildOf(): DType
//    abstract fun wildConstOf(): DType
//    abstract fun sharedOf(): DType
//    abstract fun sharedConstOf(): DType
//    abstract fun sharedWildOf(): DType
//    abstract fun sharedWildConstOf(): DType
//    abstract fun immutableOf(): DType
//
//}
//
//class TypeNull : DType() {
//    override fun implicitlyConvertibleTo(to: DType): Match {
//        val match = super.implicitlyConvertibleTo(to)
//        if (match != Match.nomatch)
//            return match
//
//        val tb: DType = to.toBaseType()
//        if (tb is TypeNull || tb is DTypePointer || tb is DTypeArray /*|| tb is TypeAarray todo*/ || tb is DTypeClass || tb is DTypeDelegate)
//            return Match.constant
//        return Match.nomatch
//    }
//}
//
//
//class TypeSlice : TypeNext() {
//    override fun implicitlyConvertibleTo(to: DType): Match {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}
//
//
//class TypeTuple : DType() {
//    override fun implicitlyConvertibleTo(to: DType): Match {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}
//
//
//class DTypeClass : DType() {
//    override fun implicitlyConvertibleTo(to: DType): Match {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}
//
//
//class DTypeEnum : DType() {
//    val sym: EnumDeclaration
//
//    constructor(sym: EnumDeclaration) : super() {
//        this.sym = sym
//    }
//
//
//    override fun implicitlyConvertibleTo(to: DType): Match {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun toBaseType(): DType {
//        if (sym.enumBody?.enumMembers != null && !sym.enumBody!!.enumMembers.isEmpty() && sym.memtype != null)
//            return this
//        val tb = sym.getMemtype().toBaseType()
//        return tb.castMod(mod)         // retain modifier bits from 'this'
//    }
//}
//
//
//class TypeStruct : DType() {
//    override fun implicitlyConvertibleTo(to: DType): Match {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}
//
//
//class TypeReturn : TypeQualified() {
//    override fun implicitlyConvertibleTo(to: DType): Match {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//}
//
//
//class TypeTypeof : TypeQualified() {
//    override fun implicitlyConvertibleTo(to: DType): Match {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//}
//
//
//class TypeInstance : TypeQualified() {
//    override fun implicitlyConvertibleTo(to: DType): Match {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}
//
//class TypeIdentifier : TypeQualified() {
//    override fun implicitlyConvertibleTo(to: DType): Match {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}
//
//abstract class TypeQualified : DType()
//
//class DTypeFunction : TypeNext() {
//    override fun implicitlyConvertibleTo(to: DType): Match {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}
//
//
//class DTypeDelegate : TypeNext() {
//    override fun implicitlyConvertibleTo(to: DType): Match {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}
//
//
//class TypeReference : TypeNext() {
//    override fun implicitlyConvertibleTo(to: DType): Match {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}
//
//
//class DTypePointer : TypeNext() {
//    override fun implicitlyConvertibleTo(to: DType): Match {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}
//
//
//open class DTypeArray : TypeNext() {
//    override fun implicitlyConvertibleTo(to: DType): Match {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}
//
//
//class TypeDArray : DTypeArray() {
//    override fun implicitlyConvertibleTo(to: DType): Match {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}
//
//
//class TypeSArray : DTypeArray() {
//    override fun implicitlyConvertibleTo(to: DType): Match {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}
//
//class TypeVector : DType() {
//    val baseType: DType? = null
//
//    override fun implicitlyConvertibleTo(to: DType): Match {
//        if (this == to) {//todo override .equals
//            return Match.exact
//        }
//        if (to is TypeVector) {
//            return Match.convert
//        }
//        return Match.nomatch
//    }
//}
//
//abstract class TypeBasic : DType() {
//    enum class TypeOf {
//        UINT, INT, CHAR//...todo
//    }
//}
//
///**
// * represents types with some kind of type modifier. Not modifiers like const,immutable etc. Modifiers like int *, int[], int function(), ref int
// */
//abstract class TypeNext : DType() {
//    val next: DType? = null
//    fun transitive() {
//        next!!.mods.addAll(next.mods)
//    }
//}
//
//
////class TypeError : DType() {
////    override fun implicitlyConvertibleTo(): Boolean {
////        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
////    }
////
////}
//
//
//class TypeInfoDeclaration {
//////todo
//}
//
//
