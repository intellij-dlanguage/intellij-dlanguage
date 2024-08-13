package io.github.intellij.dlanguage.messagebus

import com.intellij.util.messages.Topic

/**
 * @author Samael Bate (singingbush)
 * Created on 03/02/2018.
 *
 * @since v1.16
 * All the potential Topics that we intend to use should be placed here for convenience
 */
class Topics {

    companion object {
        // a change to the dub.json or dub.sdl file
        @JvmField val DUB_FILE_CHANGE = Topic.create("dub file change", DubChangeNotifier::class.java)

        // ToolChangeListeners are for when the path or args for the various D Tools are changed
        @JvmField val DUB_TOOL_CHANGE = Topic.create("dub tool change", ToolChangeListener::class.java)
        @JvmField val DCD_CLIENT_TOOL_CHANGE = Topic.create("dcd client tool change", ToolChangeListener::class.java)
        @JvmField val DCD_SERVER_TOOL_CHANGE = Topic.create("dcd server tool change", ToolChangeListener::class.java)
        @JvmField val DSCANNER_TOOL_CHANGE = Topic.create("dscanner tool change", ToolChangeListener::class.java)
        @JvmField val DFMT_TOOL_CHANGE = Topic.create("dfmt tool change", ToolChangeListener::class.java)
        @JvmField val GDB_TOOL_CHANGE = Topic.create("gdb tool change", ToolChangeListener::class.java)
    }
}
