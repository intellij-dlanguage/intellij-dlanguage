package io.github.intellij.dlanguage

import com.intellij.AbstractBundle
import com.intellij.openapi.diagnostic.Logger
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey
import java.lang.ref.Reference
import java.lang.ref.SoftReference
import java.util.*

object DlangBundle {

    val log: Logger = Logger.getInstance(javaClass)

    private var dLangBundle: Reference<ResourceBundle>? = null

    @NonNls private const val BUNDLE_ID: String = "i18n"

    init {
        // For testing alternative languages just use JVM args, such as: "-Duser.language=fr -Duser.country=FR"
        log.info("initialising D Language Bundle for locale: ${Locale.getDefault()}")
    }

    fun message(@PropertyKey(resourceBundle = BUNDLE_ID) key: String, vararg params: Any): String {
        log.trace(String.format("Getting message: %s, %s", key, params))

        return AbstractBundle.message(getBundle(), key, params)
    }

    private fun getBundle(): ResourceBundle {
        var bundle: ResourceBundle? = null

        if (dLangBundle != null) {
            bundle = dLangBundle?.get()
        }

        if (bundle == null) {
            bundle = ResourceBundle.getBundle(BUNDLE_ID)
            dLangBundle = SoftReference(bundle)
        }
        return bundle!!
    }
}
