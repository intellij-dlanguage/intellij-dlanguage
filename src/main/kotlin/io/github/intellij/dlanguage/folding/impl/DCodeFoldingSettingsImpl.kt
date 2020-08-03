package io.github.intellij.dlanguage.folding.impl

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.RoamingType
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import io.github.intellij.dlanguage.folding.DCodeFoldingSettings

@State(name = "DCodeFoldingSettings",
    storages = [Storage(value = "editor.codeinsight.xml", roamingType = RoamingType.DISABLED)]
)
class DCodeFoldingSettingsImpl : DCodeFoldingSettings(), PersistentStateComponent<DCodeFoldingSettingsImpl> {

    override var collapsibleOneLineMethods: Boolean = true

    override fun getState(): DCodeFoldingSettingsImpl = this

    override fun loadState(state: DCodeFoldingSettingsImpl) = XmlSerializerUtil.copyBean(state, this)

}
