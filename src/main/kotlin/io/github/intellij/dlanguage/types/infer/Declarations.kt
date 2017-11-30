package io.github.intellij.dlanguage.types.infer

import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.psi.*
import io.github.intellij.dlanguage.types.DType
import io.github.intellij.dlanguage.types.DTypeUnknown

fun inferDeclarationType(psi: PsiElement): DType =
    when (psi) {
        is DLanguageVariableDeclaration -> inferDeclarationType(psi.type!!)
        is DLanguageAutoDeclaration -> inferAutoDeclaration(psi)
        is DLanguageType -> inferDeclarationType(psi.type_2!!)
        is DLanguageType_2 -> inferType2(psi)
        else -> DTypeUnknown
    }

fun inferType2(psi: DLanguageType_2): DType {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

fun inferAutoDeclaration(psi: DLanguageAutoDeclaration): DType {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}
