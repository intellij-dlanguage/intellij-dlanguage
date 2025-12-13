package io.github.intellij.dlanguage.lsp

import com.intellij.platform.lsp.api.LspServerNotificationsHandler
import com.intellij.testFramework.UsefulTestCase
import io.github.intellij.dlanguage.lsp.dto.WorkspaceStateParams

import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

// no need for LightPlatformTestCase as this test does not use platform features
class ServeDLanguageClientTest : UsefulTestCase() {

    private lateinit var client: ServeDLanguageClient

    override fun setUp() {
        super.setUp()

        val handler: LspServerNotificationsHandler = mock<LspServerNotificationsHandler>()
        whenever { handler.logMessage(any()) }.then { invocation -> print(invocation.arguments.toString()) }
        this.client = ServeDLanguageClient(handler)
    }

    fun testChangedSelectedWorkspace() {
        val params = WorkspaceStateParams(
            uri = "",
            name = "",
            initialized = true,
            selected = true,
            pendingErrors = emptyMap()
        )
        this.client.changedSelectedWorkspace(params)

        assertTrue(true) // todo: get some actual verification here once implementation is complete
    }
}
