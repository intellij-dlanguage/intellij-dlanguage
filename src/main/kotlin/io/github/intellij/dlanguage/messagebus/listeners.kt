package io.github.intellij.dlanguage.messagebus


interface DcdToolChangeListener {
    fun onDcdToolChange()
}

interface DfmtToolChangeListener {
    fun onDfmtToolChange()
}

interface DscannerToolChangeListener {
    fun onDscannerToolChange()
}

interface GdbToolChangeListener {
    fun onGdbToolChange()
}

interface DubToolChangeListener {
    fun onDubToolChange()
}

interface DubFileChangeNotifier {
    fun onDubFileChange()
}
