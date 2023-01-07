package io.github.intellij.dub.tools

import com.intellij.execution.process.ProcessAdapter
import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.process.ProcessOutputTypes
import com.intellij.openapi.util.Key
import java.util.*

/**
 * @author Samael Bate (singingbush)
 * created on 06/02/18
 */
class DubProcessListener : ProcessAdapter() {
    // use StringBuffer because StringBuilder is not thread safe
    private val output = StringBuffer() // all output from stdout and stderr
    private val stdout = StringBuffer()
    private val errors = ArrayList<String>()

    override fun onTextAvailable(event: ProcessEvent, outputType: Key<*>) {
        when (outputType) {
            ProcessOutputTypes.STDOUT -> stdout.append(event.text)
            ProcessOutputTypes.STDERR -> errors.add(event.text.removeSuffix("\n"))
            //ProcessOutputTypes.SYSTEM
        }
        output.append(event.text) // everything gets add to the general output
    }

    fun getOutput() = output.toString()
    fun getStdOut() = stdout.toString()
    fun getStdOutReader() = getStdOut().byteInputStream().bufferedReader()
    fun getErrors(): List<String> = errors
}
