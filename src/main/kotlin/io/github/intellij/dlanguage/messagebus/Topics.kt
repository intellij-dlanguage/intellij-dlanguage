package io.github.intellij.dlanguage.messagebus

import com.intellij.ProjectTopics
import com.intellij.util.messages.Topic

/**
 * @author Samael Bate (singingbush)
 * Created on 03/02/2018.
 *
 * All the potential Topics that we intend to use should be placed here for convenience
 */
class Topics {

    companion object {

        // Topics provided by the Jetbrains API that we're likely to use
        @JvmField val PROJECT_ROOT_CHANGED = ProjectTopics.PROJECT_ROOTS
        @JvmField val MODULES_CHANGED = ProjectTopics.MODULES

        // a dub change could be a change to the tool itself of a change to the dub.json/sdl file
        @JvmField val DUB_CHANGE = Topic.create("dub change", DubChangeNotifier::class.java)

        // a DCD change can be either for the client or the server
        @JvmField val DCD_TOOL_CHANGE = Topic.create("dcd change", DcdToolChangeListener::class.java)

        @JvmField val DFMT_TOOL_CHANGE = Topic.create("dfmt change", DfmtToolChangeListener::class.java)
        @JvmField val DSCANNER_TOOL_CHANGE = Topic.create("dscanner change", DscannerToolChangeListener::class.java)
        @JvmField val GDB_TOOL_CHANGE = Topic.create("gdb change", GdbToolChangeListener::class.java)
    }
}
