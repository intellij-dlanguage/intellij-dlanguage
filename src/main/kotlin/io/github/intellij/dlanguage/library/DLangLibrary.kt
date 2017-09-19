package io.github.intellij.dlanguage.library

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.ProjectBundle
import com.intellij.openapi.projectRoots.ui.Util
import com.intellij.openapi.roots.JavadocOrderRootType
import com.intellij.openapi.roots.OrderRootType
import com.intellij.openapi.roots.libraries.ui.AttachRootButtonDescriptor
import com.intellij.openapi.roots.libraries.ui.LibraryRootsComponentDescriptor
import com.intellij.openapi.roots.libraries.ui.OrderRootTypePresentation
import com.intellij.openapi.roots.libraries.ui.RootDetector
import com.intellij.openapi.roots.ui.configuration.libraryEditor.DefaultLibraryRootsComponentDescriptor
import com.intellij.openapi.roots.ui.configuration.libraryEditor.LibraryEditor
import com.intellij.openapi.vfs.VirtualFile
import io.github.intellij.dlanguage.DlangBundle
import io.github.intellij.dlanguage.icons.DlangIcons
import java.util.*
import javax.swing.JComponent


class DLanguageLibraryRootsComponentDescriptor : LibraryRootsComponentDescriptor() {

    override fun getRootTypePresentation(type: OrderRootType): OrderRootTypePresentation? {
        if (type == io.github.intellij.dlanguage.library.LibFileRootType.getInstance()) {
            return OrderRootTypePresentation("Lib File", io.github.intellij.dlanguage.icons.DlangIcons.FILE)
        } else {
            return DefaultLibraryRootsComponentDescriptor.getDefaultPresentation(type)
        }
    }

    override fun getRootDetectors(): List<RootDetector> {
        return Arrays.asList(
            io.github.intellij.dlanguage.library.DlangLibRootDetector(OrderRootType.CLASSES, DlangBundle.message("sources.root.detector.sources.name")),
            io.github.intellij.dlanguage.library.DlangLibRootDetector(LibFileRootType.getInstance(), DlangBundle.message("sources.root.detector.lib.name")))
    }

    override fun createAttachButtons(): List<AttachRootButtonDescriptor> {
        return Arrays.asList(AttachUrlJavadocDescriptor())
    }

    private class AttachUrlJavadocDescriptor : AttachRootButtonDescriptor(
            JavadocOrderRootType.getInstance(),
            ProjectBundle.message("module.libraries.javadoc.url.button")
    ) {

        override fun selectFiles(parent: JComponent,
                                 initialSelection: VirtualFile?,
                                 contextModule: Module?,
                                 libraryEditor: LibraryEditor): Array<VirtualFile> {
            val vFile = Util.showSpecifyJavadocUrlDialog(parent)
            if (vFile != null) {
                return arrayOf(vFile)
            }
            return VirtualFile.EMPTY_ARRAY
        }
    }
}
