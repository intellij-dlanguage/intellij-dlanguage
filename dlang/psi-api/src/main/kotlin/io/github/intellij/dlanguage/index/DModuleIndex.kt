package io.github.intellij.dlanguage.index

import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.containers.ContainerUtil
import com.intellij.util.indexing.*
import com.intellij.util.io.EnumeratorStringDescriptor
import com.intellij.util.io.KeyDescriptor
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.psi.DlangPsiFile
import java.util.*


/**
 * This index keep a track of all modules names and allow to find the corresponding file.
 */
class DModuleIndex : ScalarIndexExtension<String>() {

    override fun getName(): ID<String, Void> = D_MODULE_INDEX

    override fun getIndexer(): DataIndexer<String, Void, FileContent> = INDEXER

    override fun getKeyDescriptor(): KeyDescriptor<String> = KEY_DESCRIPTOR

    override fun getInputFilter(): FileBasedIndex.InputFilter = D_MODULE_FILTER

    override fun dependsOnFileContent(): Boolean = true

    override fun getVersion(): Int = INDEX_VERSION

    private class MyDataIndexer : DataIndexer<String, Void, FileContent> {
        override fun map(inputData: FileContent): Map<String, Void?> {
            val psiFile = inputData.psiFile
            if (psiFile is DlangPsiFile) {
                val moduleName = psiFile.getFullyQualifiedModuleName()
                if (psiFile.name == "package.d") {
                    val directoryBasedModuleName = getDirectoryBasedModuleName(inputData)
                    // A package.d file can have a module declaration changing completely his name and so both names are valid
                    if (directoryBasedModuleName != null && moduleName != directoryBasedModuleName) {
                        return mapOf(moduleName to null, directoryBasedModuleName to null)
                    }
                }
                return Collections.singletonMap<String, Void>(moduleName, null)
            }
            return Collections.emptyMap()
        }

        /**
         * Return the module name of the file based on the directory based hierarchy of the project.
         */
        private fun getDirectoryBasedModuleName(inputData: FileContent): String? {
            var directoryBasedModuleName: String? = null
            var current = inputData.file.parent
            val moduleSourceRoot = ProjectRootManager.getInstance(inputData.project).fileIndex.getSourceRootForFile(inputData.file)
            moduleSourceRoot?: return null
            while (current != moduleSourceRoot) {
                directoryBasedModuleName = if (directoryBasedModuleName != null) {
                    current.name + "." + directoryBasedModuleName
                } else {
                    current.name
                }
                current = current.parent?: break
            }
            return directoryBasedModuleName
        }
    }

    companion object {
        val D_MODULE_FILTER = FileBasedIndex.InputFilter { file -> file.fileType === DlangFileType }
        private val D_MODULE_INDEX = ID.create<String, Void>("DModuleIndex")
        private const val INDEX_VERSION = 1
        private val KEY_DESCRIPTOR = EnumeratorStringDescriptor()
        private val INDEXER = MyDataIndexer()

        @Deprecated("Only Logger and constant should be in the companion object!")
        fun getFilesByModuleName(project: Project,
                                 moduleName: String,
                                 searchScope: GlobalSearchScope): List<DlangPsiFile> {
            val psiManager = PsiManager.getInstance(project)

            val virtualFiles = FileBasedIndex.getInstance().getContainingFiles(D_MODULE_INDEX, moduleName, searchScope)

            return ContainerUtil.mapNotNull(virtualFiles) { virtualFile: VirtualFile? ->
                val psiFile = psiManager.findFile(virtualFile!!)
                if (psiFile is DlangPsiFile) psiFile else null
            }
        }
    }
}
