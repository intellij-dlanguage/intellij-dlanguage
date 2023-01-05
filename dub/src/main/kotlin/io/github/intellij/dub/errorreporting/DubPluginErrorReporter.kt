package io.github.intellij.dub.errorreporting

import io.github.intellij.dlanguage.errorreporting.DErrorReporter

/*
* Needed to extend the error reporter in the main plugin and reference this class to prevent errors due to the classloader
*/
class DubPluginErrorReporter : DErrorReporter()
