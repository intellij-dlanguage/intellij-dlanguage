package io.github.intellij.dlanguage.types.infer

import io.github.intellij.dlanguage.psi.DLanguageFunctionDeclaration
import io.github.intellij.dlanguage.types.infer.context.DInferenceContext

fun inferTypesIn(fn: DLanguageFunctionDeclaration): DInferenceResult =
    DInferenceContext().run { preventRecursion { infer(fn) } }

private val threadLocalGuard: ThreadLocal<Boolean> = ThreadLocal.withInitial { false }

/**
 * This function asserts that code inside a lambda don't call itself recursively.
 */
private inline fun <T> preventRecursion(action: () -> T): T {
    if (threadLocalGuard.get()) error("Can not run nested type inference")
    threadLocalGuard.set(true)
    try {
        return action()
    } finally {
        threadLocalGuard.set(false)
    }
}
