package io.github.intellij.dlanguage.types.infer

import io.github.intellij.dlanguage.psi.*
import io.github.intellij.dlanguage.psi.ext.containsToken
import io.github.intellij.dlanguage.types.*

fun inferDeclarationType(psi: DLanguageDeclaration): DType {
    val type = when {
        psi.variableDeclaration != null -> inferVariableDeclaration(psi.variableDeclaration!!)
        else -> DTypeUnknown
    }

    return when {
        psi.attributes.containsToken(DlangTypes.KW_CONST) -> type.toConst()
        psi.attributes.containsToken(DlangTypes.KW_IMMUTABLE) -> type.toImmutable()
        psi.attributes.containsToken(DlangTypes.KW_SHARED) -> type.toShared()
        else -> type
    }
}

private fun inferVariableDeclaration(psi: DLanguageVariableDeclaration): DType =
    when {
        psi.autoDeclaration != null -> inferAutoDeclaration(psi.autoDeclaration!!)
        psi.type != null -> inferType2(psi.type!!.type_2!!)
        else -> DTypeUnknown
    }

private fun inferType2(psi: DLanguageType_2): DType {
    val type = psi.children.firstOrNull()

    if (type is DLanguageBuiltinType) {
        return DTypePrimitive.fromBuiltinPsiElement(type)
    }

    return DTypeUnknown
}

private fun inferAutoDeclaration(psi: DLanguageAutoDeclaration): DType {
    var inferredType: DType = DTypeUnknown

    psi.autoDeclarationParts.firstOrNull()?.let {
        inferredType = inferExprType(it.initializer)
        val storageClass = psi.storageClass ?: return inferredType

        inferredType = when {
            storageClass.containsToken(DlangTypes.KW_CONST) -> inferredType.toConst()
            storageClass.containsToken(DlangTypes.KW_IMMUTABLE) -> inferredType.toImmutable()
            storageClass.containsToken(DlangTypes.KW_SHARED) -> inferredType.toShared()
            else -> inferredType
        }
    }

    return inferredType
}
