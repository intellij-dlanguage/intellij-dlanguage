package io.github.intellij.dlanguage.messagebus

import com.intellij.ProjectTopics
import com.intellij.util.messages.Topic

/**
 * @author Samael Bate (singingbush)
 * Created on 03/02/2018.
 *
 * All the potential Topics that we intend to use should be placed here for convenience
 */
interface Topics {

    companion object {

        // Topics provided by the Jetbrains API that we're likely to use
        val PROJECT_ROOT_CHANGED = ProjectTopics.PROJECT_ROOTS
        val MODULES_CHANGED = ProjectTopics.MODULES

        val DUB_FILE_CHANGE = Topic.create("dub file change", DubFileChangeNotifier::class.java)

        // Topics for the various D Tools
        val DUB_TOOL_CHANGE = Topic.create("dub toolchain change", DubToolChangeListener::class.java)
        val DCD_TOOL_CHANGE = Topic.create("dcd toolchain change", DcdToolChangeListener::class.java)
        val DFMT_TOOL_CHANGE = Topic.create("dfmt toolchain change", DfmtToolChangeListener::class.java)
        val DSCANNER_TOOL_CHANGE = Topic.create("dscanner toolchain change", DscannerToolChangeListener::class.java)
        val GDB_TOOL_CHANGE = Topic.create("gdb toolchain change", GdbToolChangeListener::class.java)
    }
}
