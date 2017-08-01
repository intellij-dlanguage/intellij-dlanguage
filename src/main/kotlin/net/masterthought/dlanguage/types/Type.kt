package net.masterthought.dlanguage.types

import net.masterthought.dlanguage.utils.ClassDeclaration
import net.masterthought.dlanguage.utils.EnumDeclaration
import net.masterthought.dlanguage.utils.TemplateDeclaration


/**
 * Created by francis on 7/29/2017.
 */

val tvoid: Type? = null
val tint8: Type? = null
val tuns8: Type? = null
val tint16: Type? = null
val tuns16: Type? = null
val tint32: Type? = null
val tuns32: Type? = null
val tint64: Type? = null
val tuns64: Type? = null
val tint128: Type? = null
val tuns128: Type? = null
val tfloat32: Type? = null
val tfloat64: Type? = null
val tfloat80: Type? = null
val timaginary32: Type? = null
val timaginary64: Type? = null
val timaginary80: Type? = null
val tcomplex32: Type? = null
val tcomplex64: Type? = null
val tcomplex80: Type? = null
val tbool: Type? = null
val tchar: Type? = null
val twchar: Type? = null
val tdchar: Type? = null

// Some special types
val tshiftcnt: Type? = null
val tvoidptr: Type? = null    // void*
val tstring: Type? = null     // immutable(char)[]
val twstring: Type? = null    // immutable(wchar)[]
val tdstring: Type? = null    // immutable(dchar)[]
val tvalist: Type? = null     // va_list alias
val terror: Type? = null      // for error recovery
val tnull: Type? = null       // for null type

val tsize_t: Type? = null     // matches size_t alias
val tptrdiff_t: Type? = null  // matches ptrdiff_t alias
val thash_t: Type? = null     // matches hash_t alias


val dtypeinfo: ClassDeclaration? = null
val typeinfoclass: ClassDeclaration? = null
val typeinfointerface: ClassDeclaration? = null
val typeinfostruct: ClassDeclaration? = null
val typeinfopointer: ClassDeclaration? = null
val typeinfoarray: ClassDeclaration? = null
val typeinfostaticarray: ClassDeclaration? = null
val typeinfoassociativearray: ClassDeclaration? = null
val typeinfovector: ClassDeclaration? = null
val typeinfoenum: ClassDeclaration? = null
val typeinfofunction: ClassDeclaration? = null
val typeinfodelegate: ClassDeclaration? = null
val typeinfotypelist: ClassDeclaration? = null
val typeinfoconst: ClassDeclaration? = null
val typeinfoinvariant: ClassDeclaration? = null
val typeinfoshared: ClassDeclaration? = null
val typeinfowild: ClassDeclaration? = null

val rtinfo: TemplateDeclaration? = null

//Type[TMAX] basic;
//StringTable stringtable;
//todo check transative const

enum class Match {
    nomatch, // no match
    convert, // match with conversions
    constant, // match with conversion to const
    exact      // exact match

}


abstract class ResolvableType {
    abstract fun resolve(): Type
    abstract fun actuallyIsType(): Boolean
    //todo
}

abstract class Type {
    //shared immutable should not be allowed
    val mods: MutableSet<Modifiers> = mutableSetOf()
    var vtinfo: TypeInfoDeclaration? = null     // TypeInfo object for this Type
    open fun implicitlyConvertibleTo(to: Type): Match {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    open fun toBaseType(): Type {
        return this
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }


}

class TypeNull : Type() {
    override fun implicitlyConvertibleTo(to: Type): Match {
        val match = super.implicitlyConvertibleTo(to)
        if (match != Match.nomatch)
            return match

        val tb: Type = to.toBaseType()
        if (tb is TypeNull || tb is TypePointer || tb is TypeArray /*|| tb is TypeAarray todo*/ || tb is TypeClass || tb is TypeDelegate)
            return Match.constant
        return Match.nomatch
    }
}


class TypeSlice : TypeNext() {
    override fun implicitlyConvertibleTo(to: Type): Match {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


class TypeTuple : Type() {
    override fun implicitlyConvertibleTo(to: Type): Match {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


class TypeClass : Type() {
    override fun implicitlyConvertibleTo(to: Type): Match {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


class TypeEnum : Type() {
    val sym: EnumDeclaration

    constructor(sym: EnumDeclaration) : super() {
        this.sym = sym
    }


    override fun implicitlyConvertibleTo(to: Type): Match {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toBaseType(): Type {
        if (sym.enumBody?.enumMembers != null && !sym.enumBody!!.enumMembers.isEmpty() && !sym.memtype)
            return this
        val tb = sym.getMemtype(Loc()).toBasetype()
        return tb.castMod(mod)         // retain modifier bits from 'this'
    }
}


class TypeStruct : Type() {
    override fun implicitlyConvertibleTo(to: Type): Match {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


class TypeReturn : TypeQualified() {
    override fun implicitlyConvertibleTo(to: Type): Match {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


class TypeTypeof : TypeQualified() {
    override fun implicitlyConvertibleTo(to: Type): Match {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


class TypeInstance : TypeQualified() {
    override fun implicitlyConvertibleTo(to: Type): Match {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


class TypeIdentifier : TypeQualified() {
    override fun implicitlyConvertibleTo(to: Type): Match {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


abstract class TypeQualified : Type()


class TypeFunction : TypeNext() {
    override fun implicitlyConvertibleTo(to: Type): Match {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


class TypeDelegate : TypeNext() {
    override fun implicitlyConvertibleTo(to: Type): Match {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


class TypeReference : TypeNext() {
    override fun implicitlyConvertibleTo(to: Type): Match {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


class TypePointer : TypeNext() {
    override fun implicitlyConvertibleTo(to: Type): Match {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


open class TypeArray : TypeNext() {
    override fun implicitlyConvertibleTo(to: Type): Match {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


class TypeDArray : TypeArray() {
    override fun implicitlyConvertibleTo(to: Type): Match {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


class TypeSArray : TypeArray() {
    override fun implicitlyConvertibleTo(to: Type): Match {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class TypeVector : Type() {
    val baseType: Type? = null

    override fun implicitlyConvertibleTo(to: Type): Match {
        if (this == to) {//todo override .equals
            return Match.exact
        }
        if (to is TypeVector) {
            return Match.convert
        }
        return Match.nomatch
    }

}

abstract class TypeBasic : Type() {
    enum class TypeOf {
        UINT, INT, CHAR//...todo
    }
}

/**
 * represents types with sme kind of type modifier. Not modifiers like const,immutable etc. Modifiers like int *, int[], int function(), ref int
 */
abstract class TypeNext : Type() {
    val next: Type? = null
    fun transitive() {
        next!!.mods.addAll(next.mods)
    }
}


//class TypeError : Type() {
//    override fun implicitlyConvertibleTo(): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//}


class TypeInfoDeclaration {
////todo
}

enum class Modifiers

