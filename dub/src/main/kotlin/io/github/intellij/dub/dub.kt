package io.github.intellij.dub

import com.intellij.openapi.externalSystem.model.ProjectSystemId
import com.intellij.openapi.util.IconLoader

class Dub {

    companion object {
        @JvmField val SYSTEM_ID = ProjectSystemId("DUB", "dub")
        @JvmField val BALL = IconLoader.getIcon("/icons/dub-ball.png", Dub::class.java)
        const val NOTIFICATION_GROUP_ID = "dub-notifications"
    }
}
