package io.github.intellij.dlanguage.lsp.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.RoamingType
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.util.xmlb.annotations.Tag
import org.jetbrains.annotations.Nls

/*
* When developing you can find "dlang.served.xml" in "idea-sandbox/config/options" to view the xml structure
*/
@State(
    name = "ServeDSettings",
    storages = [Storage(value = "dlang.served.xml", roamingType = RoamingType.LOCAL)],
    presentableName = ServeDSettingsState.PresentableNameGetter::class,
)
open class ServeDSettingsState : PersistentStateComponent<ServeDSettingsState.ServeDConfigSettings> {

    private object PresentableNameGetter : State.NameGetter() {
        override fun get(): @Nls String? = "Serve-D settings" // todo: use i18n
    }

    // needs default value
    private var settings: ServeDConfigSettings = ServeDConfigSettings()

//    override fun initializeComponent() {
//        thisLogger().info("ServeDSettingsState initializeComponent")
//    }

//    override fun noStateLoaded() {
//        this.settings = ServeDConfigSettings()
//    }

    override fun loadState(state: ServeDConfigSettings) {
        this.settings = state // When using Kotlin DO NOT USE: "XmlSerializerUtil.copyBean(state, settings)"
    }

    override fun getState(): ServeDConfigSettings = this.settings // ?: ServeDConfigSettings()


    // The settings should only be saved locally as they cannot roam different machines
    @Tag("served")
    data class ServeDConfigSettings(
        @Tag("path") var binaryPath: String = "",
        @Tag("acceptedPreviewFeature") var acceptedPreviewFeature: Boolean = false
    ) : Comparable<ServeDConfigSettings> {
        override fun compareTo(other: ServeDConfigSettings): Int = compareValuesBy(this, other,
            { it.binaryPath },
            { it.acceptedPreviewFeature },
        )
    }

}
