package net.masterthought.dlanguage

import com.intellij.CommonBundle
import com.intellij.openapi.diagnostic.Logger
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey
import java.lang.ref.Reference
import java.lang.ref.SoftReference
import java.util.*

object DlangBundle {

    val log: Logger = Logger.getInstance(javaClass)

    var dLangBundle: Reference<ResourceBundle>? = null

    @NonNls private const val BUNDLE_ID: String = "i18n"

    init {
        val locale = Locale.getDefault()
        log.info("initialising D Language Bundle for locale: ${locale.toLanguageTag()}")
    }

    fun message(@PropertyKey(resourceBundle = BUNDLE_ID) key: String, vararg params: Any): String {
        log.debug("Getting message: {}, {}", key, params)

        return CommonBundle.message(getBundle(), key, params)
    }

    private fun getBundle(): ResourceBundle {
        var bundle: ResourceBundle? = null

        if (dLangBundle != null) {
            bundle = dLangBundle?.get()
        }

        if (bundle == null) {
            bundle = ResourceBundle.getBundle(BUNDLE_ID)
            dLangBundle = SoftReference<ResourceBundle>(bundle)
        }
        return bundle!!
    }
}
