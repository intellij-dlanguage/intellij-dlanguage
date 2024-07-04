package io.github.intellij.dlanguage

import com.intellij.icons.AllIcons
import com.intellij.lang.Language
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.LayeredIcon
import javax.swing.Icon

object DLanguage : Language("D") {
    private fun readResolve(): Any = DLanguage

    const val MODULE_TYPE_ID = "DLANGUAGE_MODULE"

    override fun isCaseSensitive(): Boolean = true

    class Icons {
        companion object {
            @JvmField val FILE = IconLoader.getIcon("/icons/d.png", DLanguage::class.java)
            @JvmField val MODULE = FILE
            @JvmField val SDK = FILE
            @JvmField val SDK_ADD = FILE
            @JvmField val RUN = FILE
            @JvmField val LIBRARY = FILE

            @JvmField val SRC_FILE = IconLoader.getIcon("/icons/d-file.png", DLanguage::class.java)
            @JvmField val SRC_FILE_RUNNABLE: Icon = LayeredIcon.layeredIcon(arrayOf(SRC_FILE, AllIcons.Nodes.RunnableMark))
            @JvmField val SRC_FILE_PACKAGE = IconLoader.getIcon("/icons/d-file-package.png", DLanguage::class.java)

            @JvmField val CLASS = FILE

            @JvmField val NODE_CLASS = AllIcons.Nodes.Class
            @JvmField val NODE_INTERFACE = AllIcons.Nodes.Interface
            @JvmField val NODE_STRUCT = IconLoader.getIcon("/icons/nodes/struct.png", DLanguage::class.java)
            @JvmField val NODE_UNION = IconLoader.getIcon("/icons/nodes/union.png", DLanguage::class.java)
            @JvmField val NODE_ENUM = AllIcons.Nodes.Enum
            @JvmField val NODE_ENUM_MEMBER =LayeredIcon.layeredIcon(arrayOf(AllIcons.Nodes.Field, AllIcons.Nodes.StaticMark, AllIcons.Nodes.FinalMark))
            @JvmField val NODE_FUNCTION = IconLoader.getIcon("/icons/nodes/function.png", DLanguage::class.java)
            @JvmField val NODE_METHOD = AllIcons.Nodes.Method
            @JvmField val NODE_FIELD = AllIcons.Nodes.Field
            @JvmField val NODE_PROPERTY = AllIcons.Nodes.Property
            @JvmField val NODE_PROPERTY_GETTER = IconLoader.getIcon("/icons/nodes/propertyGetter.png", DLanguage::class.java)
            @JvmField val NODE_PROPERTY_SETTER = IconLoader.getIcon("/icons/nodes/propertySetter.png", DLanguage::class.java)
            @JvmField val NODE_ALIAS = IconLoader.getIcon("/icons/nodes/alias.png", DLanguage::class.java)
            @JvmField val NODE_MIXIN = IconLoader.getIcon("/icons/nodes/mixin.png", DLanguage::class.java)
        }
    }

}
