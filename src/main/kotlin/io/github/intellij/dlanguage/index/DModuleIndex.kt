package io.github.intellij.dlanguage.index

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.containers.ContainerUtil
import com.intellij.util.indexing.*
import com.intellij.util.io.EnumeratorStringDescriptor
import com.intellij.util.io.KeyDescriptor
import io.github.intellij.dlanguage.DlangFileType
import io.github.intellij.dlanguage.psi.DlangFile
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
        override fun map(inputData: FileContent): Map<String, Void> {
            val psiFile = inputData.psiFile
            if (psiFile is DlangFile) {
                val moduleName: String = psiFile.getFullyQualifiedModuleName()
                return Collections.singletonMap<String, Void>(moduleName, null)
            }
            return Collections.emptyMap()
        }
    }

    companion object {
        val D_MODULE_FILTER = FileBasedIndex.InputFilter { file -> file.fileType === DlangFileType.INSTANCE }
        private val D_MODULE_INDEX = ID.create<String, Void>("DModuleIndex")
        private const val INDEX_VERSION = 0
        private val KEY_DESCRIPTOR = EnumeratorStringDescriptor()
        private val INDEXER = MyDataIndexer()

        fun getFilesByModuleName(project: Project,
                                 moduleName: String,
                                 searchScope: GlobalSearchScope): List<DlangFile> {
            val psiManager = PsiManager.getInstance(project)
            val virtualFiles = getVirtualFilesByModuleName(moduleName, searchScope)
            return ContainerUtil.mapNotNull(virtualFiles) {
                virtualFile: VirtualFile? ->
                val psiFile = psiManager.findFile(virtualFile!!)
                if (psiFile is DlangFile) psiFile else null
            }
        }

        fun getVirtualFilesByModuleName(moduleName: String,
                                        searchScope: GlobalSearchScope): Collection<VirtualFile> {
            return FileBasedIndex.getInstance().getContainingFiles(D_MODULE_INDEX, moduleName, searchScope)
        }

    }
}
